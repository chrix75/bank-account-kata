package com.github.chrix75.unittests.thebank.domain.statement;


import com.github.chrix75.thebank.domain.operations.BankingOperation;
import com.github.chrix75.thebank.domain.statement.AccountStatement;
import com.github.chrix75.thebank.utils.statement.MapAccountStatementLineConverter;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.github.chrix75.thebank.domain.Money.amountInEuro;
import static org.junit.Assert.assertEquals;

public class AccountStatementTest {


    @Test
    public void converts_banking_operations_into_list_of_maps() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<BankingOperation> operations = Arrays.asList(
                new BankingOperation(LocalDate.of(2019, 10, 1), amountInEuro(-80), amountInEuro(100)),
                new BankingOperation(LocalDate.of(2019, 10, 4), amountInEuro(30), amountInEuro(130)));

        AccountStatement<Map<String, String>> statement = new AccountStatement<>(new MapAccountStatementLineConverter(dtf), operations);

        List<Map<String, String>> lines = statement.lines();

        assertEquals(2, lines.size());
        List<Map<String, String>> expectedLines = expectedLines();
        assertEquals(expectedLines, lines);
    }

    @Test
    public void converts_no_banking_operations_into_list_of_maps() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<BankingOperation> operations = Collections.emptyList();
        AccountStatement<Map<String, String>> statement = new AccountStatement<>(new MapAccountStatementLineConverter(dtf), operations);

        List<Map<String, String>> lines = statement.lines();

        assertEquals(0, lines.size());
    }

    private List<Map<String, String>> expectedLines() {
        List<Map<String, String>> expected = new ArrayList<>();
        expected.add(addExpectedLine("2019-10-01", "-80", "100"));
        expected.add(addExpectedLine("2019-10-04", "30", "130"));
        return expected;
    }

    private Map<String, String> addExpectedLine(String date, String amount, String balance) {
        Map<String, String> line = new HashMap<>();
        line.put("date", date);
        line.put("amount", amount);
        line.put("balance", balance);
        return line;
    }
}