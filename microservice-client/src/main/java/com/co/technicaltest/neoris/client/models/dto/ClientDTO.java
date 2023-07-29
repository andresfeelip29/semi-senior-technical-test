package com.co.technicaltest.neoris.client.models.dto;

import domain.models.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ClientDTO(@NotNull @NotBlank String name, @NotNull @NotBlank String password,
                        @NotNull Gender gender, @NotNull @Positive Integer age,
                        @NotNull @NotBlank String identification, @NotNull @NotBlank String address,
                        @NotNull @NotBlank String phone, @NotNull Boolean status) {
}




