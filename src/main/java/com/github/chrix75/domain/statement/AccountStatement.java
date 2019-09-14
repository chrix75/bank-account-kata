package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.operations.BankingOperation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Définit un relevé bancaire.
 * <p>
 * Je considére qu'un relevé bancaire est une visualisation des opérations bancaires. Cette visualisation peut avoir
 * différentes formes que j'ai traduit par la définition d'un type de représentation.
 *
 * @param <T> Type de la représentation d'une ligne du relevé bancaire
 */
final public class AccountStatement<T> {
    private final AccountStatementLineConverter<T> lineConverter;

    public AccountStatement(AccountStatementLineConverter<T> lineConverter) {
        this.lineConverter = lineConverter;
    }

    public List<T> lines(List<BankingOperation> operations) {
        return operations.stream().map(lineConverter::convert).collect(Collectors.toList());
    }
}
