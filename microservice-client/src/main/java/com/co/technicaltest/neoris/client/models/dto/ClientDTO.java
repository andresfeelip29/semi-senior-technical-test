package com.co.technicaltest.neoris.client.models.dto;

import domain.models.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ClientDTO(@NotNull @NotBlank String name, @NotNull @NotBlank String password,
                        @NotNull @NotBlank Gender gender, @NotNull @NotBlank Integer age,
                        @NotNull @NotBlank String identification, @NotNull @NotBlank String address,
                        @NotNull @NotBlank String phone, @NotNull Boolean status) {
}




