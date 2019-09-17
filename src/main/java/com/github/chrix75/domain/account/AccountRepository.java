package com.github.chrix75.domain.account;

public interface AccountRepository {
    void saveAccount(Account account);

    /**
     * Retourne le compte lié à un numéro de compte.
     * <p>
     * Cette méthode n'est pas une fonction de recherche mais une fonction de récupération.
     * Cela signifie que si le numéro de compte n'existe pas alors une exception est levée au lieu de retourner une
     * valeur null.
     * <p>
     * Si on acceptait un  numéro de compte inconnu, il faudrait mieux signaler la possibilité de ne pas trouver un
     * compte en modifiant le type de la valeur retournée en {@code Optional<Account>}.
     *
     * @param number Numéro du compte voulu.
     * @return Le compte bancaire {@link Account} correspond au numéro fourni.
     * @throws IllegalArgumentException est levé si le numéro de compte est inconnu.
     */
    Account accountByNumber(AccountNumber number);

    boolean accountExists(AccountNumber number);
}
