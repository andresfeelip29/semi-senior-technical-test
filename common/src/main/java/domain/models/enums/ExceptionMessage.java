package domain.models.enums;

public enum ExceptionMessage {

    CLIENT_NOT_FOUND("No existe cliente registrado en sistema con id: %d!"),
    CLIENT_NOT_FOUND_IN_MICROSERVICE_CLIENT("No existe cliente en microservicios clientes registrado en sistema con id: %d!"),
    CLIENT_NOT_FOUND_IN_MICROSERVICE_ACCOUNT("No existe cuenta en microservicios cuentas registrado en sistema con id: %d!"),
    ACCOUNT_NOT_ASSIGNED_TO_CLIENT("Cuenta con id: %d no se encuentra asignada a usuario con id: %d, por favor valide informacion!"),
    ACCOUNT_NOT_FOUND("No existe cuenta registrada en sistema con id: %d!"),
    MOVEMENT_NOT_FOUND("No existe movimiento registrado en sistema con id: %d!"),
    ACCOUNT_TYPE_NOT_FOUND("No existe el tipo de cuenta: %s , registrada en sistema!"),
    ACCOUNT_ASSOCIATED_TO_CLIENT_NO_FOUND("No existe una cuenta con id: %d , asociado a algun usuario en microservicio de clientes!"),
    ACCOUNT_HAS_NOT_BALANCE_FOR_DEBIT_TRANSACTION("Saldo no disponible"),
    BALANCE_UPDATE_ERROR("Error al actualiza el balance de la cuenta con id: %d"),
    CLIENT_ASSOCIATED_TO_ACCOUNT_NO_FOUND("No existe un usuario con id: %d , asociado a alguna cuenta en microservicio de cuentas!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
