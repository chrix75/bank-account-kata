package com.github.chrix75.domain;

import com.github.chrix75.domain.operations.BankingOperationException;

final public class OperationAmount {
    private final Money value;

    public OperationAmount(Money value) {
        if (value == null || value.isZero()) {
            throw new BankingOperationException("A banking operation amount can't be 0.");
        }
        this.value = value;
    }

    public Money value() {
        return this.value;
    }

    public boolean greaterThanZero() {
        return value.greaterThanZero();
    }

    public boolean isZero() {
        return value.isZero();
    }
}
