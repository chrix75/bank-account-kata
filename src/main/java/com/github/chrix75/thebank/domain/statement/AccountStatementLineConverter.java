package com.github.chrix75.thebank.domain.statement;

import com.github.chrix75.thebank.domain.operations.BankingOperation;

/**
 * @param <T> Type de représentation dans le relevé bancaire.
 */
public interface AccountStatementLineConverter<T> {
    T convert(BankingOperation operation);
}
