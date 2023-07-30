package com.co.technicaltest.neoris.client.models;


import domain.models.enums.BankAccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountNumber;
    private BankAccountType accountType;
    private BigDecimal initialBalance;
    private Boolean status;

}
