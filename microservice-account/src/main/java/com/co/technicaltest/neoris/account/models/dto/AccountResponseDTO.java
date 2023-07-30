package com.co.technicaltest.neoris.account.models.dto;

import com.co.technicaltest.neoris.account.models.Client;
import com.co.technicaltest.neoris.account.models.entity.AccountType;

import java.math.BigDecimal;

public record AccountResponseDTO(Long id, String accountNumber, AccountType accountType,
                                 BigDecimal intialBalance, Boolean status, Client client) {
}
