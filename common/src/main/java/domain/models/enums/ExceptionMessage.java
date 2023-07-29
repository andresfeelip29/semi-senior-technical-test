package domain.models.enums;

public enum ExceptionMessage {

    CLIENT_NOT_FOUND("No existe cliente registrado en sistema!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
