package com.github.chrix75.unittests.thebank.domain.account;

import com.github.chrix75.thebank.domain.account.AccountNumber;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountNumberTest {

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void creates_a_valid_account_number() {
        AccountNumber number = new AccountNumber("123456789");
    }

    @Test
    public void creates_an_account_number_without_value() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The account number is not a 9-digits value.");
        AccountNumber number = new AccountNumber(null);
    }

    @Test
    public void creates_an_account_number_with_a_too_short_value() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The account number is not a 9-digits value.");
        AccountNumber number = new AccountNumber("");
    }

    @Test
    public void creates_an_account_number_with_a_too_long_value() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("The account number is not a 9-digits value.");
        AccountNumber number = new AccountNumber("1234567890");
    }

}