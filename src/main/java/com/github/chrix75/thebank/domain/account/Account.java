package com.github.chrix75.thebank.domain.account;

import com.github.chrix75.thebank.domain.Money;
import com.github.chrix75.thebank.domain.OperationAmount;
import com.github.chrix75.thebank.domain.operations.BankingOperation;
import com.github.chrix75.thebank.domain.operations.BankingOperationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Pour le kata, les opérations du compte sont stockées directement dans une {@link List}.
 * Dans un cas réel, cette solution ne sera pas optimale (stockage de toutes les opérations en mémoire, gestion du
 * chargement) et la référence des opérations devraient être gérées par un autre composant.
 * <p>
 * J'ai également défini pour cette implémentation qu'un compte bancaire ne peut pas être à découvert.
 *
 * Remarque: Je n'ai pas fait référence au client du compte bancaire vu qu'il n'y avait aucune action demandée sur cette
 * entité.
 */
final public class Account {
    private final AccountNumber number;
    private Money balance;
    private final List<BankingOperation> operations;

    public Account(AccountNumber number, Money balance) {
        this.number = number;
        this.balance = balance;
        this.operations = new ArrayList<>();
    }

    public Money balance() {
        return balance;
    }

    public AccountNumber number() {
        return this.number;
    }

    public void depositMoney(OperationAmount amount, LocalDate operationDate) {
        if (amount.greaterThanZero()) {
            updateBalance(amount.value());
            operations.add(new BankingOperation(operationDate, amount.value(), balance));
        } else {
            throw new BankingOperationException("A deposit must be greater than 0");
        }
    }

    private void updateBalance(Money amount) {
        Money updatedBalance = balance.plus(amount);

        if (invalidAccountBalance(updatedBalance)) {
            throw new BankingOperationException("The balance must always be equals or greater than 0.");
        }

        balance = updatedBalance;
    }

    public void withdrawMoney(OperationAmount amount, LocalDate operationDate) {
        if (amount.isZero()) {
            throw new BankingOperationException("A withdrawal cannot be 0");
        }

        Money standardizedAmount = standardizedAmount(amount.value());
        updateBalance(standardizedAmount);
        operations.add(new BankingOperation(operationDate, standardizedAmount, balance));
    }

    private boolean invalidAccountBalance(Money updatedBalance) {
        return !updatedBalance.isZero() && !updatedBalance.greaterThanZero();
    }

    private Money standardizedAmount(Money amount) {
        return amount.greaterThanZero() ? amount.negate() : amount;
    }

    public String currency() {
        return balance.currency();
    }

    public List<BankingOperation> allOperations() {
        return new ArrayList<>(operations);
    }

    public int operationCount() {
        return operations.size();
    }
}
