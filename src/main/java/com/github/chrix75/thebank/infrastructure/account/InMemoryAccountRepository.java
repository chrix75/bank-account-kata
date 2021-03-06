package com.github.chrix75.thebank.infrastructure.account;

import com.github.chrix75.thebank.domain.account.Account;
import com.github.chrix75.thebank.domain.account.AccountNumber;
import com.github.chrix75.thebank.domain.account.AccountRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Pour le Kata et les tests, cette implémentation permet de gérer le stockage des comptes bancaires de façon autonome.
 */
final public class InMemoryAccountRepository implements AccountRepository {

    private final Map<AccountNumber, Account> accounts = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        accounts.put(account.number(), account);
    }

    @Override
    public Account accountByNumber(AccountNumber number) {
        if (!accounts.containsKey(number)) {
            throw new IllegalArgumentException("Account number " + number + " not found.");
        }
        return accounts.get(number);
    }

    @Override
    public boolean accountExists(AccountNumber number) {
        return accounts.containsKey(number);
    }

    public void cleanAccountRepository() {
        accounts.clear();
    }


}
