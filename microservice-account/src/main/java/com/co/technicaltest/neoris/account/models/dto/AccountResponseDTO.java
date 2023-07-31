package com.co.technicaltest.neoris.account.models.dto;

import com.co.technicaltest.neoris.account.models.Client;
import domain.models.enums.BankAccountType;

import java.math.BigDecimal;

public record AccountResponseDTO(Long id, String accountNumber, BankAccountType accountType,
                                 BigDecimal initialBalance, Boolean status, Client client) {
}
