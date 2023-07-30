package com.co.technicaltest.neoris.account.exceptions;

import domain.exception.DomainException;

public class AccountTypeNotFoundException extends DomainException {
    public AccountTypeNotFoundException(String message) {
        super(message);
    }

    public AccountTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
