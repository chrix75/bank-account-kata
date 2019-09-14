package com.github.chrix75.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Objet valeur représentant un montant d'argent dans une devise.
 */
final public class Money {
    private static final List<String> VALID_CURRENCIES = Arrays.asList("EU", "US");

    private final BigDecimal amount;
    private final String currency;

    public static Money amountInEuro(Integer amount) {
        return new Money(BigDecimal.valueOf(amount), "EU");
    }

    public Money(BigDecimal amount, String currency) {
        if (amount == null) {
            throw new IllegalArgumentException("No amount provided.");
        }

        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("A currency must be provided.");
        }
        if (!VALID_CURRENCIES.contains(currency)) {
            throw new IllegalArgumentException("Unknown currency: " + currency);
        }

        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Money && ((Money) obj).amount.compareTo(amount) == 0 && ((Money) obj).currency.equals(currency);
    }

    @Override
    public String toString() {
        return amount.toString() + currency;
    }

    public Money plus(Money other) {
        if (!other.currency.equals(currency)) {
            throw new IllegalArgumentException("Different currencies.");
        }

        return new Money(amount.add(other.amount), currency);
    }

    public Money convertedAmount(Double rate, String currency) {
        BigDecimal conversion = amount.multiply(BigDecimal.valueOf(rate));
        return new Money(conversion, currency);
    }

    public String currency() {
        return currency;
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }

    public boolean greaterThanZero() {
        return amount.signum() > 0;
    }

    public Money negate() {
        return new Money(amount.negate(), currency);
    }

    public BigDecimal amount() {
        return amount;
    }
}
