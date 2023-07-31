package com.co.technicaltest.neoris.account.models.dto;


import domain.models.enums.BankAccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record AccountDTO(@NotNull BankAccountType accountType, @NotNull @Positive BigDecimal initialBalance,
                         @NotNull Boolean status, @Positive Long clientId) { }
