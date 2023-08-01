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
import java.util.Objects;

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
    private Boolean state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return getId().equals(movement.getId()) && getCreateAt().equals(movement.getCreateAt()) && getMovementType() == movement.getMovementType() && getMovementValue().equals(movement.getMovementValue()) && getBalance().equals(movement.getBalance()) && getClientId().equals(movement.getClientId()) && getName().equals(movement.getName()) && getAccountId().equals(movement.getAccountId()) && getAccountNumber().equals(movement.getAccountNumber()) && getAccountType().equals(movement.getAccountType()) && getState().equals(movement.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreateAt(), getMovementType(), getMovementValue(), getBalance(), getClientId(), getName(), getAccountId(), getAccountNumber(), getAccountType(), getState());
    }
}
