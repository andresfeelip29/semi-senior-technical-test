package com.co.technicaltest.neoris.movement.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovementResponseDTO(LocalDateTime createAt, String name, String accountNumber,
                                  String accountType, BigDecimal initialBalance, Boolean state,
                                  BigDecimal movementValue, BigDecimal balance) { }
