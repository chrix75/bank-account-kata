package com.github.chrix75.domain.account;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.OperationAmount;
import com.github.chrix75.domain.exchange.MoneyExchangeService;
import com.github.chrix75.domain.operations.BankingOperationException;

import java.time.LocalDate;

final public class AccountService {

    private final AccountRepository accountRepository;
    private final MoneyExchangeService moneyExchangeService;

    public AccountService(AccountRepository accountRepository, MoneyExchangeService moneyExchangeService) {
        this.accountRepository = accountRepository;
        this.moneyExchangeService = moneyExchangeService;
    }

    public void makeDeposit(AccountNumber number, Money amount, LocalDate operationDate) {
        Account account = accountRepository.accountByNumber(number);
        account.depositMoney(new OperationAmount(amount), operationDate);
    }

    public void makeWithdrawal(AccountNumber number, Money amount, LocalDate operationDate) {
        Account account = accountRepository.accountByNumber(number);

        if (sameCurrency(amount, account)) {
            account.withdrawMoney(new OperationAmount(amount), operationDate);
        } else {
            Money convertedAmount = moneyExchangeService.convertedAmount(amount, account.currency());
            account.withdrawMoney(new OperationAmount(convertedAmount), operationDate);
        }

    }

    private boolean sameCurrency(Money amount, Account account) {
        return account.currency().equals(amount.currency());
    }

    public void openNewAccount(AccountNumber number, Money balance) {
        if (!balance.currency().equals("EU")) {
            throw new BankingOperationException("Only account in Euro are accepted.");
        }

        if (accountRepository.accountExists(number)) {
            throw new BankingOperationException("Account with number " + number + " already exists.");
        }

        Account account = new Account(number, balance);
        accountRepository.saveAccount(account);
    }
}
