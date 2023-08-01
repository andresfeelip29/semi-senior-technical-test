package com.co.technicaltest.neoris.movement.models;

import domain.models.enums.Gender;

public record Client(Long id, String name, Gender gender, Integer age,
                     String identification, String address, String phone,
                     Boolean status) {
}
