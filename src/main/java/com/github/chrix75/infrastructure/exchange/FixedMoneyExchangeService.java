package com.github.chrix75.infrastructure.exchange;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.exchange.MoneyExchangeService;

import java.util.HashMap;
import java.util.Map;

/**
 * Implémentation de l'interface {@link MoneyExchangeService} qui simule un service fournissant le taux de change d'une
 * devise.
 */
final public class FixedMoneyExchangeService implements MoneyExchangeService {
    private final Map<String, Double> rates = new HashMap<>();

    public void changeRate(String fromCurrency, String toCurrency, Double rate) {
        rates.put(fromCurrency + "->" + toCurrency, rate);
    }

    @Override
    public Money convertedAmount(Money from, String toCurrency) {
        String keyRate = from.currency() + "->" + toCurrency;
        Double rate = rates.get(keyRate);
        if (rate == null) {
            throw new IllegalArgumentException("No rate for money conversion " + keyRate);
        }

        return from.convertedAmount(rate, toCurrency);
    }
}
