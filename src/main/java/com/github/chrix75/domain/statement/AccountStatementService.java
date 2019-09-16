package com.github.chrix75.domain.statement;

import com.github.chrix75.domain.account.AccountNumber;

/**
 * Interface du service charagé de produire le relevé bancaire d'un compte.
 * <p>
 * La paramétrisation est mis en place pour pouvoir gérer différents formats pour l'impression du relevé bancaire.
 *
 * @param <T> Type de lignes du relevé bancaire
 */
public interface AccountStatementService<T> {
    AccountStatement<T> accountStatement(AccountNumber number);
}
