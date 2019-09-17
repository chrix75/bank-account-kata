package com.github.chrix75.thebank.domain.account;

/**
 * Pour ce projet, un numéro de compte doit être de 9 chiffres.
 */
final public class AccountNumber {
    private final String value;

    public AccountNumber(String value) {
        if (value == null || !value.matches("\\d{9}")) {
            throw new IllegalArgumentException("The account number is not a 9-digits value.");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
