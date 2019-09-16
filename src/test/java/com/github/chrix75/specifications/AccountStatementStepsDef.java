package com.github.chrix75.specifications;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.OperationAmount;
import com.github.chrix75.domain.account.Account;
import com.github.chrix75.domain.operations.BankingOperation;
import com.github.chrix75.domain.statement.AccountStatement;
import com.github.chrix75.domain.statement.AccountStatementService;
import com.github.chrix75.infrastructure.account.InMemoryAccountRepository;
import com.github.chrix75.infrastructure.statement.SimpleMapAccountStatementService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java8.En;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AccountStatementStepsDef implements En {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<BankingOperation> bankingOperations;
    private AccountStatementService accountStatementService = new SimpleMapAccountStatementService(TestContext.accountRepository);

    @After
    public void resetAccountRepository() {
        InMemoryAccountRepository repository = (InMemoryAccountRepository) (TestContext.accountRepository);
        repository.cleanAccountRepository();
    }

    public AccountStatementStepsDef() {
        Given("the following banking operations made by Stephan", (DataTable table) -> {
            Account account = TestContext.accountRepository.accountByNumber(TestContext.accountNumber);

            table.asMaps().forEach(line -> {
                processLineAsBankingOperation(account, line);
            });
        });

        When("Stephan asks the account statement", () -> {
            Account account = TestContext.accountRepository.accountByNumber(TestContext.accountNumber);
            bankingOperations = account.allOperations();
        });

        Then("he gets the following statement printing", (DataTable table) -> {
            List<Map<String, String>> expectedLines = table.asMaps();
            AccountStatement<Map<String, String>> accountStatement = accountStatementService.accountStatement(TestContext.accountNumber);
            List<Map<String, String>> statementLines = accountStatement.lines();
            for (int i = 0; i < expectedLines.size(); ++i) {
                Map<String, String> expectedLine = expectedLines.get(i);
                Map<String, String> currentOperation = statementLines.get(i);
                assertEquals(expectedLine, currentOperation);
            }
        });
    }

    private void processLineAsBankingOperation(Account account, Map<String, String> line) {
        LocalDate operationDate = LocalDate.parse(line.get("date"), dateFormatter);
        BigDecimal amount = new BigDecimal(line.get("amount"));
        if (amount.signum() >= 0) {
            Money money = new Money(amount, "EU");
            account.depositMoney(new OperationAmount(money), operationDate);
        } else {
            Money money = new Money(amount.negate(), "EU");
            account.withdrawMoney(new OperationAmount(money), operationDate);
        }
    }
}
