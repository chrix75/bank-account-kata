package com.github.chrix75.unittests.domain.account;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.account.Account;
import com.github.chrix75.domain.account.AccountNumber;
import com.github.chrix75.domain.account.AccountRepository;
import com.github.chrix75.domain.account.AccountService;
import com.github.chrix75.domain.operations.BankingOperationException;
import com.github.chrix75.infrastructure.account.InMemoryAccountRepository;
import com.github.chrix75.infrastructure.exchange.FixedMoneyExchangeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.chrix75.domain.Money.amountInEuro;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.*;

public class AccountServiceTest {

    private AccountRepository accountRepository = new InMemoryAccountRepository();
    private FixedMoneyExchangeService moneyExchangeService = new FixedMoneyExchangeService();
    private AccountService accountService = new AccountService(accountRepository, moneyExchangeService);
    private final AccountNumber accountNumber = new AccountNumber("123456789");

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        accountRepository = new InMemoryAccountRepository();
        moneyExchangeService = new FixedMoneyExchangeService();
        accountService = new AccountService(accountRepository, moneyExchangeService);
    }

    @Test
    public void creates_a_new_account() {
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        Account account = accountRepository.accountByNumber(accountNumber);
        assertEquals(account.number(), accountNumber);
        assertEquals(account.currency(), "EU");
        assertEquals(account.balance().toString(), "100EU");
    }

    @Test
    public void tries_to_create_two_accounts_with_same_number() {
        exceptionRule.expect(BankingOperationException.class);
        exceptionRule.expectMessage("Account with number 123456789 already exists.");

        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.openNewAccount(accountNumber, amountInEuro(50));
    }

    @Test
    public void makes_a_deposit_in_euro() {
        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.makeDeposit(accountNumber, amountInEuro(50), operationDate);
        Account account = accountRepository.accountByNumber(accountNumber);
        assertEquals(0, valueOf(150).compareTo(account.balance().amount()));
        assertEquals(1, account.operationCount());
    }

    @Test
    public void makes_a_deposit_in_us_dollars() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Different currencies.");

        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        try {
            accountService.openNewAccount(accountNumber, amountInEuro(100));
            accountService.makeDeposit(accountNumber, new Money(BigDecimal.TEN, "US"), operationDate);
        } catch (Exception e) {
            throw e;
        } finally {
            Account account = accountRepository.accountByNumber(accountNumber);
            assertEquals(0, valueOf(100).compareTo(account.balance().amount()));
            assertEquals(0, account.operationCount());
        }

    }

    @Test
    public void makes_a_deposit_of_zero_euro() {
        exceptionRule.expect(BankingOperationException.class);
        exceptionRule.expectMessage("A banking operation amount can't be 0.");

        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.makeDeposit(accountNumber, new Money(BigDecimal.ZERO, "EU"), operationDate);
    }

    @Test
    public void makes_a_withdrawal_in_euro() {
        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.makeWithdrawal(accountNumber, amountInEuro(50), operationDate);

        Account account = accountRepository.accountByNumber(accountNumber);
        assertEquals(0, valueOf(50).compareTo(account.balance().amount()));
        assertEquals(1, account.operationCount());
    }

    @Test
    public void makes_a_withdrawal_in_dollars() {
        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        moneyExchangeService.changeRate("US", "EU", 0.8);
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.makeWithdrawal(accountNumber, new Money(valueOf(50), "US"), operationDate);

        Account account = accountRepository.accountByNumber(accountNumber);
        assertEquals(0, valueOf(60).compareTo(account.balance().amount()));
        assertEquals(1, account.operationCount());
    }

    @Test
    public void withdraws_all_money_from_account() {
        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        accountService.openNewAccount(accountNumber, amountInEuro(100));
        accountService.makeWithdrawal(accountNumber, amountInEuro(100), operationDate);

        Account account = accountRepository.accountByNumber(accountNumber);
        assertEquals(0, BigDecimal.ZERO.compareTo(account.balance().amount()));
        assertEquals(1, account.operationCount());
    }

    @Test
    public void tries_to_withdraw_to_much_money() {
        exceptionRule.expect(BankingOperationException.class);
        exceptionRule.expectMessage("The balance must always be equals or greater than 0.");

        LocalDate operationDate = LocalDate.of(2019, 10, 1);
        try {
            accountService.openNewAccount(accountNumber, amountInEuro(100));
            accountService.makeWithdrawal(accountNumber, amountInEuro(101), operationDate);
        } catch (Exception e) {
            throw e;
        } finally {
            Account account = accountRepository.accountByNumber(accountNumber);
            assertEquals(0, valueOf(100).compareTo(account.balance().amount()));
            assertEquals(0, account.operationCount());
        }
    }

}