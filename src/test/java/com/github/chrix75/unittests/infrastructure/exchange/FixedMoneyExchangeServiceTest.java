package com.github.chrix75.unittests.infrastructure.exchange;

import com.github.chrix75.domain.Money;
import com.github.chrix75.infrastructure.exchange.FixedMoneyExchangeService;
import org.junit.Test;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.*;

public class FixedMoneyExchangeServiceTest {

    @Test
    public void converts_US_dollars_into_euro() {
        FixedMoneyExchangeService exchangeService = new FixedMoneyExchangeService();
        exchangeService.changeRate("US", "EU", 0.5);
        Money inEuro = exchangeService.convertedAmount(new Money(valueOf(100), "US"), "EU");
        assertEquals(0, inEuro.amount().compareTo(valueOf(50)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void converts_US_dollars_into_unknwon_currency() {
        FixedMoneyExchangeService exchangeService = new FixedMoneyExchangeService();
        exchangeService.changeRate("US", "EU", 0.5);
        Money inEuro = exchangeService.convertedAmount(new Money(valueOf(100), "US"), "DKK");
    }
}