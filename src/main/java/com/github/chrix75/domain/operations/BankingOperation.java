package com.github.chrix75.domain.operations;

import com.github.chrix75.domain.Money;

import java.time.LocalDate;

public class BankingOperation {
    private final LocalDate date;
    private final Money amount;
    private final Money balance;

    public BankingOperation(LocalDate date, Money amount, Money balance) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public LocalDate date() {
        return date;
    }

    public Money amount() {
        return amount;
    }

    public Money balanceAfter() {
        return balance;
    }
}
