package com.co.technicaltest.neoris.movement.models.dto;

import domain.models.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record MovementDTO(@Positive Long clientId, @Positive Long accountId,
                          @NotNull BigDecimal value, @NotNull MovementType movementType) {
}
