package com.github.chrix75.domain.exchange;

import com.github.chrix75.domain.Money;

/**
 * Interface définissant le service de conversion entre devise.
 */
public interface MoneyExchangeService {
    Money convertedAmount(Money from, String toCurrency);
}
