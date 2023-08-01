package com.co.technicaltest.neoris.movement.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovementResponseDTO(String id, @JsonFormat(pattern="dd/MM/yyyy HH:mm") LocalDateTime createAt, String name, String accountNumber,
                                  String accountType, BigDecimal initialBalance, Boolean state,
                                  BigDecimal movementValue, BigDecimal balance) { }
