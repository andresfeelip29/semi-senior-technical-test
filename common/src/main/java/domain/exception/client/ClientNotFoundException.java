package domain.exception.client;

import domain.exception.DomainException;

public class ClientNotFoundException extends DomainException {
    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
