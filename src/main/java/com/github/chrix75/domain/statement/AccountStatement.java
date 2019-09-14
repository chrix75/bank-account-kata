package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

import java.util.List;
import java.util.stream.Collectors;

final public class AccountStatement<T> {
    private final AccountStatementLineConverter<T> lineConverter;

    public AccountStatement(AccountStatementLineConverter<T> lineConverter) {
        this.lineConverter = lineConverter;
    }

    public List<T> lines(List<BankingOperation> operations) {
        return operations.stream().map(lineConverter::convert).collect(Collectors.toList());
    }
}
