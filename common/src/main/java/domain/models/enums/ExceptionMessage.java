package domain.models.enums;

public enum ExceptionMessage {

    CLIENT_NOT_FOUND("No existe cliente registrado en sistema con id: %d!"),
    ACCOUNT_NOT_FOUND("No existe cuenta registrada en sistema con id: %d!"),
    ACCOUNT_TYPE_NOT_FOUND("No existe el tipo de cuenta: %s , registrada en sistema!"),
    ACCOUNT_ASSOCIATED_TO_CLIENT_NO_FOUND("No existe una cuenta con id: %d , asociado a algun usuario en microservicio de clientes!"),
    CLIENT_ASSOCIATED_TO_ACCOUNT_NO_FOUND("No existe un usuario con id: %d , asociado a alguna cuenta en microservicio de cuentas!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
