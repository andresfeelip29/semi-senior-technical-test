package domain.exception.client;

import domain.exception.DomainException;

public class ClientAccountNotFoundException extends DomainException {
    public ClientAccountNotFoundException(String message) {
        super(message);
    }

    public ClientAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
