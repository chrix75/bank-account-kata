package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

/**
 * Interface définissant la conversion d'une opération bancaire en une ligne du relevé bancaire.
 *
 * @param <T> Type de représentation dans le relevé bancaire.
 */
public interface AccountStatementLineConverter<T> {
    T convert(BankingOperation operation);
}
