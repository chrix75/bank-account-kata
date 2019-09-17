package com.github.chrix75.domain.operations;

/**
 * J'ai choisi d'utiliser une exception du type unchecked pour alléger le développement du code appelant.
 */
public class BankingOperationException extends RuntimeException {
    public BankingOperationException(String message) {
        super(message);
    }
}
