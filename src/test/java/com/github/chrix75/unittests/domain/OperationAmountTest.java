package com.github.chrix75.unittests.domain;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.OperationAmount;
import com.github.chrix75.domain.operations.BankingOperationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class OperationAmountTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void creates_a_banking_operation() {
        OperationAmount operationAmount = new OperationAmount(Money.amountInEuro(100));
        assertEquals(Money.amountInEuro(100), operationAmount.value());
    }

    @Test
    public void creates_invalid_operation() {
        exceptionRule.expect(BankingOperationException.class);
        exceptionRule.expectMessage("A banking operation amount can't be 0.");
        OperationAmount operationAmount = new OperationAmount(Money.amountInEuro(0));
    }


}