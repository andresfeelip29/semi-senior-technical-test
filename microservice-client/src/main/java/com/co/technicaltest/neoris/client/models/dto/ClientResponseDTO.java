package com.co.technicaltest.neoris.client.models.dto;


import com.co.technicaltest.neoris.client.models.Account;
import domain.models.enums.Gender;

import java.util.List;

public record ClientResponseDTO(Long id, String name, Gender gender, Integer age,
                                String identification, String address, String phone,
                                Boolean status, List<Integer> clientAccounts, List<Account> clientAccountsDetail) {
}



