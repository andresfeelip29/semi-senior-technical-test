package com.co.technicaltest.neoris.account.models.dto;

import com.co.technicaltest.neoris.account.models.entity.AccountType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record AccountDTO(@NotNull AccountType accountType, @NotNull @Positive BigDecimal intialBalance,
                         @NotNull Boolean status, @Positive Long clientId) { }
