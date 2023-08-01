package com.co.technicaltest.neoris.movement.exceptions;

import domain.exception.DomainException;

public class AccountAndClientNotValidRelationshipException extends DomainException {
    public AccountAndClientNotValidRelationshipException(String message) {
        super(message);
    }

    public AccountAndClientNotValidRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
