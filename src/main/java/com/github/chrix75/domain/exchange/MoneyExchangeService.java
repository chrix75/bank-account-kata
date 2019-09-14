package com.github.chrix75.domain.exchange;

import com.github.chrix75.domain.Money;

public interface MoneyExchangeService {
    Money convertedAmount(Money from, String toCurrency);
}
