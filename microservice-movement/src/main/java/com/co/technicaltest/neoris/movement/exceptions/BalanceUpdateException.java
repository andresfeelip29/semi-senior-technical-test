package com.co.technicaltest.neoris.movement.exceptions;

import domain.exception.DomainException;

public class BalanceUpdateException extends DomainException {
    public BalanceUpdateException(String message) {
        super(message);
    }

    public BalanceUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
