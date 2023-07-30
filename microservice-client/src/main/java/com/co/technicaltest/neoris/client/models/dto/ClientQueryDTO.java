package com.co.technicaltest.neoris.client.models.dto;

import domain.models.enums.Gender;

public record ClientQueryDTO(Long id, String name, Gender gender, Integer age,
                             String identification, String address, String phone,
                             Boolean status) {
}
