package domain.models;

import jakarta.validation.constraints.Positive;

public record ClientAccountQueryDTO(@Positive Long accountId, @Positive Long clientId) {
}
