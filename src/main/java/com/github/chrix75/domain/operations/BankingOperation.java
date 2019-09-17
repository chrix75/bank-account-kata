package com.github.chrix75.domain.operations;

import com.github.chrix75.domain.Money;

import java.time.LocalDate;

public class BankingOperation {
    private final LocalDate date;
    private final Money amount;
    private final Money balanceAfter;

    /**
     * Crée un nouvelle opération bancaire
     *
     * @param date    date de l'opération
     * @param amount  montant de l'opération qui doit être en euro
     * @param balanceAfter solde du compte bancaire après cette opération
     */
    public BankingOperation(LocalDate date, Money amount, Money balanceAfter) {
        if (!amount.inEuro()) {
            throw new IllegalArgumentException("Banking operation must be in euro.");
        }
        this.date = date;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public LocalDate date() {
        return date;
    }

    public Money amount() {
        return amount;
    }

    public Money balanceAfter() {
        return balanceAfter;
    }
}
