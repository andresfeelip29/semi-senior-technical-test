package com.co.technicaltest.neoris.account.exceptions;

import domain.exception.DomainException;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
