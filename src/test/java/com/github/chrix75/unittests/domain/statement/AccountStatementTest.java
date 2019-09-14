package com.github.chrix75.unittests.domain.statement;


import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.operations.BankingOperation;
import com.github.chrix75.domain.statement.AccountStatement;
import com.github.chrix75.utils.statement.MapAccountStatementLineConverter;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.github.chrix75.domain.Money.amountInEuro;
import static org.junit.Assert.*;

public class AccountStatementTest {


    @Test
    public void converts_banking_operations_into_list_of_maps() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AccountStatement<Map<String, String>> statement = new AccountStatement<>(new MapAccountStatementLineConverter(dtf));

        List<BankingOperation> operations = Arrays.asList(
                new BankingOperation(LocalDate.of(2019, 10, 1), amountInEuro(-80), amountInEuro(100)),
                new BankingOperation(LocalDate.of(2019, 10, 4), amountInEuro(30), amountInEuro(130)));

        List<Map<String, String>> lines = statement.lines(operations);

        assertEquals(2, lines.size());
        List<Map<String, String>> expectedLines = expectedLines();
        assertEquals(expectedLines, lines);
    }

    @Test
    public void converts_no_banking_operations_into_list_of_maps() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        AccountStatement<Map<String, String>> statement = new AccountStatement<>(new MapAccountStatementLineConverter(dtf));

        List<BankingOperation> operations = Collections.emptyList();
        List<Map<String, String>> lines = statement.lines(operations);

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