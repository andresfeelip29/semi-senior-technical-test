package com.co.technicaltest.neoris.account.models.dto;

import domain.models.enums.BankAccountType;

import java.math.BigDecimal;

public record AccountQueryDTO(String accountNumber, BankAccountType accountType, BigDecimal initialBalance, Boolean status) {
}
