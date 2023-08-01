package domain.exception.movement;

import domain.exception.DomainException;

public class AccountHasNoBalanceException extends DomainException {
    public AccountHasNoBalanceException(String message) {
        super(message);
    }

    public AccountHasNoBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
