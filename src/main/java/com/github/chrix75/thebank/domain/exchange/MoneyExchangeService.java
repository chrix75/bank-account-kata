package com.github.chrix75.thebank.domain.exchange;

import com.github.chrix75.thebank.domain.Money;

public interface MoneyExchangeService {
    Money convertedAmount(Money from, String toCurrency);
}
