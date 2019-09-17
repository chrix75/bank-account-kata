package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Je considére qu'un relevé bancaire est une visualisation des opérations bancaires. Cette visualisation peut avoir
 * différentes formes que j'ai traduit par la définition d'un type de représentation.
 *
 * @param <T> Type de la représentation d'une ligne du relevé bancaire
 */
final public class AccountStatement<T> {
    private final AccountStatementLineConverter<T> lineConverter;
    private final List<BankingOperation> operations;

    public AccountStatement(AccountStatementLineConverter<T> lineConverter, List<BankingOperation> operations) {
        this.lineConverter = lineConverter;
        this.operations = operations;
    }

    public List<T> lines() {
        return operations.stream().map(lineConverter::convert).collect(Collectors.toList());
    }
}
