package com.github.chrix75.thebank.infrastructure.statement;

import com.github.chrix75.thebank.domain.account.Account;
import com.github.chrix75.thebank.domain.account.AccountNumber;
import com.github.chrix75.thebank.domain.account.AccountRepository;
import com.github.chrix75.thebank.domain.statement.AccountStatement;
import com.github.chrix75.thebank.domain.statement.AccountStatementLineConverter;
import com.github.chrix75.thebank.domain.statement.AccountStatementService;
import com.github.chrix75.thebank.utils.statement.MapAccountStatementLineConverter;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public class SimpleMapAccountStatementService implements AccountStatementService<Map<String, String>> {
    private final DateTimeFormatter dateFormatter;
    private final AccountRepository accountRepository;

    public SimpleMapAccountStatementService(AccountRepository accountRepository,
                                            DateTimeFormatter dateFormatter) {
        this.accountRepository = accountRepository;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public AccountStatement<Map<String, String>> accountStatement(AccountNumber number) {
        Account account = accountRepository.accountByNumber(number);
        AccountStatementLineConverter<Map<String, String>> statementLineConverter = new MapAccountStatementLineConverter(dateFormatter);
        return new AccountStatement<>(statementLineConverter, account.allOperations());
    }
}
