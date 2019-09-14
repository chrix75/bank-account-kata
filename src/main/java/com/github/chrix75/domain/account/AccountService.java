package com.github.chrix75.domain.account;

import com.github.chrix75.domain.Money;
import com.github.chrix75.domain.OperationAmount;
import com.github.chrix75.domain.exchange.MoneyExchangeService;
import com.github.chrix75.domain.operations.BankingOperationException;

import java.time.LocalDate;

/**
 * Service métier gérant les opérations d'un compte bancaires.
 * <p>
 * J'ai volontairement implémenté directement une classe. Dans le contexte actuel, je n'ai pas vu l'intêret de définir
 * une interface.
 * <p>
 * J'ai également préféré d'écrire une méthode pour les dépôts et une pour les retraits au lieu d'en avoir qu'une
 * seule qui se base sur le montant de l'opération. Cela permet de gérer certaines contraintes métiers plus facilement.
 * Cette solution est néanmoins viable car nous n'avons que 2 opérations. Si ce nombre tend à augmenter, il sera alors
 * plus sage de définir les opérations via des statégies.
 */
final public class AccountService {

    private final AccountRepository accountRepository;
    private final MoneyExchangeService moneyExchangeService;

    public AccountService(AccountRepository accountRepository, MoneyExchangeService moneyExchangeService) {
        this.accountRepository = accountRepository;
        this.moneyExchangeService = moneyExchangeService;
    }

    /**
     * Réalise un dépot en euro sur un compte bancaire.
     *
     * Les dépôt de 0 zéro ne sont pas acceptés et sont considérés comme des erreurs.
     *
     * @param number Numéro du compte concerné par le dépôt
     * @param amount Quantité d'argent déposée (en euro et cette valeur doit être &gt; 0)
     * @param operationDate Date du dépôt
     * @throws BankingOperationException est levé si le dépot ne peut pas être réalisé.
     */
    public void makeDeposit(AccountNumber number, Money amount, LocalDate operationDate) {
        Account account = accountRepository.accountByNumber(number);
        account.depositMoney(new OperationAmount(amount), operationDate);
    }

    /**
     * Réalise un retrait sur un compte bancaire.
     *
     * Cette opération accepte les retraits en euros ou dans une autre devise.
     *
     * Afin de ne pas être trop restrictif, cette méthode accepte les montants positifs et négatifs.
     *
     * @param number Numéro du compte concerné par le retrait
     * @param amount Quantité d'argent retirée (en euro et cette valeur doit être différent de 0)
     * @param operationDate Date du retrait
     * @throws BankingOperationException est levé si le retrait ne peut pas être réalisé.
     */
    public void makeWithdrawal(AccountNumber number, Money amount, LocalDate operationDate) {
        Account account = accountRepository.accountByNumber(number);

        if (sameCurrency(amount, account)) {
            account.withdrawMoney(new OperationAmount(amount), operationDate);
        } else {
            Money convertedAmount = moneyExchangeService.convertedAmount(amount, account.currency());
            account.withdrawMoney(new OperationAmount(convertedAmount), operationDate);
        }

    }

    private boolean sameCurrency(Money amount, Account account) {
        return account.currency().equals(amount.currency());
    }

    public void openNewAccount(AccountNumber number, Money balance) {
        if (!balance.currency().equals("EU")) {
            throw new BankingOperationException("Only account in Euro are accepted.");
        }

        if (accountRepository.accountExists(number)) {
            throw new BankingOperationException("Account with number " + number + " already exists.");
        }

        Account account = new Account(number, balance);
        accountRepository.saveAccount(account);
    }
}
