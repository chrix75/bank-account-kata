package com.github.chrix75.unittests.thebank.domain;

import com.github.chrix75.thebank.domain.Money;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.Assert.assertEquals;

public class MoneyTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void creates_amount_of_money_in_euro() {
        Money money = new Money(TEN, "EU");
    }

    @Test
    public void creates_amount_of_money_in_us_dollars() {
        Money money = new Money(TEN, "US");
    }

    @Test
    public void creates_amount_of_money_with_no_amount() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No amount provided.");

        Money money = new Money(null, "US");
    }

    @Test
    public void creates_amount_of_money_with_no_currency() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("A currency must be provided.");

        Money money = new Money(TEN, "");
    }

    @Test
    public void creates_amount_of_money_with_null_currency() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("A currency must be provided.");

        Money money = new Money(TEN, null);
    }

    @Test
    public void creates_amount_of_money_with_invalid_currency() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Unknown currency: DKK");

        Money money = new Money(TEN, "DKK");
    }

    @Test
    public void adds_two_amount_of_money() {
        Money a = new Money(TEN, "EU");
        Money b = new Money(ONE, "EU");
        Money c = a.plus(b);

        assertEquals(new Money(BigDecimal.valueOf(11), "EU"), c);
    }

    @Test
    public void adds_two_amount_of_money_in_different_currencies() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Different currencies.");

        Money a = new Money(TEN, "EU");
        Money b = new Money(ONE, "US");
        Money c = a.plus(b);
    }
}