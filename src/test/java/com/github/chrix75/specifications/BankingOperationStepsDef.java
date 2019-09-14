package com.github.chrix75.specifications;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.account.Account;
import com.github.chrix75.domain.account.AccountService;
import com.github.chrix75.domain.operations.BankingOperation;
import com.github.chrix75.domain.operations.BankingOperationException;
import com.github.chrix75.infrastructure.account.InMemoryAccountRepository;
import com.github.chrix75.infrastructure.exchange.FixedMoneyExchangeService;
import io.cucumber.java.After;
import io.cucumber.java8.En;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.github.chrix75.domain.Money.amountInEuro;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankingOperationStepsDef implements En {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final FixedMoneyExchangeService moneyExchangeService = new FixedMoneyExchangeService();
    private final AccountService accountService = new AccountService(TestContext.accountRepository, moneyExchangeService);
    private BankingOperationException operationException;

    @After
    public void resetAccountRepository() {
        InMemoryAccountRepository repository = (InMemoryAccountRepository) (TestContext.accountRepository);
        repository.cleanAccountRepository();
    }

    public BankingOperationStepsDef() {
        Given("Stephan opened his account with an initial deposit of (\\d+) euro", (Integer amount) -> {
            accountService.openNewAccount(TestContext.accountNumber, amountInEuro(amount));
        });

        Given("1 US dollar is worth (\\d+\\.\\d{1,2}) euro", (Double rate) -> {
            moneyExchangeService.changeRate("US", "EU", rate);
        });

        When("Stephan makes a deposit in his account of (\\d+) euro at (\\d{4}-\\d{2}-\\d{2})", (Integer amount, String date) -> {
            try {
                accountService.makeDeposit(TestContext.accountNumber,
                        amountInEuro(amount),
                        LocalDate.parse(date, dateFormatter));
            } catch (BankingOperationException e) {
                operationException = e;
            }
        });

        When("Stephan makes a withdrawal from his account of (\\d+) euro at (\\d{4}-\\d{2}-\\d{2})",
             (Integer amount, String date) -> {
                 try {
                     accountService.makeWithdrawal(TestContext.accountNumber,
                                                   amountInEuro(amount),
                                                   LocalDate.parse(date, dateFormatter));
                 } catch (BankingOperationException e) {
                     operationException = e;
                 }
             });

        When("Stephan makes a withdrawal from his account of (\\d+) US dollars at (\\d{4}-\\d{2}-\\d{2})",
             (Integer amount, String date) -> {
                 try {
                     accountService.makeWithdrawal(TestContext.accountNumber,
                                                   new Money(BigDecimal.valueOf(amount), "US"),
                                                   LocalDate.parse(date, dateFormatter));
                 } catch (BankingOperationException e) {
                     operationException = e;
                 }
             });

        Then("the new account balance is (\\d+) euro", (Integer expectedBalance) -> {
            Money expected = amountInEuro(expectedBalance);
            Account account = TestContext.accountRepository.accountByNumber(TestContext.accountNumber);
            assertEquals(expected, account.balance());
        });

        Then("the withdraw is refused", () -> {
            assertTrue(operationException instanceof BankingOperationException);
        });
    }
}
