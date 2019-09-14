package com.github.chrix75.domain.account;

public interface AccountRepository {
    void saveAccount(Account account);

    Account accountByNumber(AccountNumber number);

    boolean accountExists(AccountNumber number);
}
