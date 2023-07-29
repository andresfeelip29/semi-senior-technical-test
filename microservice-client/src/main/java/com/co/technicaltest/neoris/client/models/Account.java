package com.co.technicaltest.neoris.client.models;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Boolean status;

}
