package com.co.technicaltest.neoris.account.exceptions;

import domain.exception.DomainException;

public class AccountClientNotFoundException extends DomainException {
    public AccountClientNotFoundException(String message) {
        super(message);
    }

    public AccountClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
