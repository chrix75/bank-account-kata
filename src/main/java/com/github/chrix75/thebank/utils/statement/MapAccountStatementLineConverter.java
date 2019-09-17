package com.github.chrix75.thebank.utils.statement;

import com.github.chrix75.thebank.domain.operations.BankingOperation;
import com.github.chrix75.thebank.domain.statement.AccountStatementLineConverter;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

final public class MapAccountStatementLineConverter implements AccountStatementLineConverter<Map<String, String>> {
    private final DateTimeFormatter dateTimeFormatter;

    public MapAccountStatementLineConverter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public Map<String, String> convert(BankingOperation operation) {
        Map<String, String> map = new HashMap<>();
        map.put("date", dateTimeFormatter.format(operation.date()));
        map.put("amount", operation.amount().toString().replaceAll("[^0-9]+$", ""));
        map.put("balance", operation.balanceAfter().toString().replaceAll("[^0-9]+$", ""));
        return map;

    }
}
