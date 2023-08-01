package com.co.technicaltest.neoris.movement.models;

import domain.models.enums.BankAccountType;

import java.math.BigDecimal;

public record Account(Long id, String accountNumber, BankAccountType accountType,
                      BigDecimal initialBalance, Boolean status, Client client) {
}
