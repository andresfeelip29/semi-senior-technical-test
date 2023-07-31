package domain.models.enums;

public enum MovementType {

    CREDIT("Credito"),
    DEBIT("Debito");

    private final String type;

    MovementType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
