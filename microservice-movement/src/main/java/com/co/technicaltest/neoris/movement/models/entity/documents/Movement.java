package com.co.technicaltest.neoris.movement.models.entity.documents;

import domain.models.enums.MovementType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collection = "movimientos")
public class Movement {

    @Id
    private String id;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    @NotNull
    private LocalDateTime createAt;

    @NotNull
    private MovementType movementType;

    @NotNull
    private BigDecimal movementValue;

    @NotNull
    private BigDecimal balance;

    @NotNull
    private Long clientId;

    @NotNull
    private String name;

    @NotNull
    private Long accountId;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountType;

    @NotNull
    private BigDecimal initialBalance;

    @NotNull
    private Boolean state;


}
