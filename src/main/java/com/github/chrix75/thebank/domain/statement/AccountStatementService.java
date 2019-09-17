package com.github.chrix75.thebank.domain.statement;

import com.github.chrix75.thebank.domain.account.AccountNumber;

/**
 * La paramétrisation est mise en place pour pouvoir gérer différents formats pour l'impression du relevé bancaire.
 *
 * @param <T> Type de lignes du relevé bancaire
 */
public interface AccountStatementService<T> {
    AccountStatement<T> accountStatement(AccountNumber number);
}
