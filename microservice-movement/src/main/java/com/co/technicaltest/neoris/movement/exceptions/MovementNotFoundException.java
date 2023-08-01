package com.co.technicaltest.neoris.movement.exceptions;

import domain.exception.DomainException;

public class MovementNotFoundException extends DomainException {
    public MovementNotFoundException(String message) {
        super(message);
    }

    public MovementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
