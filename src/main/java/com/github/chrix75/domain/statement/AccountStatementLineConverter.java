package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

public interface AccountStatementLineConverter<T> {
    T convert(BankingOperation operation);
}
