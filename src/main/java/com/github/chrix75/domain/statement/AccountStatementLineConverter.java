package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

/**
 * @param <T> Type de représentation dans le relevé bancaire.
 */
public interface AccountStatementLineConverter<T> {
    T convert(BankingOperation operation);
}
