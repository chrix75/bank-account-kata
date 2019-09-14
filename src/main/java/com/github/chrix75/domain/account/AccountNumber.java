package com.github.chrix75.domain.account;

final public class AccountNumber {
    private final String value;

    public AccountNumber(String value) {
        if (value == null || value.length() != 9) {
            throw new IllegalArgumentException("The account number is not a 9-digits value.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
