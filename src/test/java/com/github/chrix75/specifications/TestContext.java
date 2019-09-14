package com.github.chrix75.specifications;

import com.github.chrix75.domain.account.AccountNumber;
import com.github.chrix75.domain.account.AccountRepository;
import com.github.chrix75.infrastructure.account.InMemoryAccountRepository;

final public class TestContext {
    static final  AccountNumber accountNumber = new AccountNumber("123456789");
    static final AccountRepository accountRepository = new InMemoryAccountRepository();

}
