package com.co.technicaltest.neoris.account.models;

import domain.models.enums.Gender;
import lombok.*;


@Data
public class Client {

    private Long id;
    private String name;
    private Gender gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    private Boolean status;
}
