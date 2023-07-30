package domain.models.enums;

public enum BankAccountType {

    SAVINGS_ACCOUNT("Ahorro"),
    CURRENT_ACCOUNT("Corriente");

    private final String type;

    BankAccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
