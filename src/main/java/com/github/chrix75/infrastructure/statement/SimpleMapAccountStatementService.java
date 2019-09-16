package com.github.chrix75.infrastructure.statement;

import com.github.chrix75.domain.account.Account;
import com.github.chrix75.domain.account.AccountNumber;
import com.github.chrix75.domain.account.AccountRepository;
import com.github.chrix75.domain.statement.AccountStatement;
import com.github.chrix75.domain.statement.AccountStatementLineConverter;
import com.github.chrix75.domain.statement.AccountStatementService;
import com.github.chrix75.utils.statement.MapAccountStatementLineConverter;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SimpleMapAccountStatementService implements AccountStatementService<Map<String, String>> {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final AccountRepository accountRepository;

    public SimpleMapAccountStatementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountStatement<Map<String, String>> accountStatement(AccountNumber number) {
        Account account = accountRepository.accountByNumber(number);
        AccountStatementLineConverter<Map<String, String>> statementLineConverter = new MapAccountStatementLineConverter(dateFormatter);
        return new AccountStatement<>(statementLineConverter, account.allOperations());
    }
}
