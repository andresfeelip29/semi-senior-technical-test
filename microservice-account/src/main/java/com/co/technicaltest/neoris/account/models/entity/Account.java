package com.co.technicaltest.neoris.account.models.entity;

import com.co.technicaltest.neoris.account.models.Client;
import domain.models.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuentas")
@Entity
public class Account extends BaseEntity {

    @NotEmpty
    @Column(name = "numero_cuenta")
    private String accountNumber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipo_cuenta_id")
    private AccountType accountType;

    @NotNull
    @Column(name = "saldo_incial")
    private BigDecimal initialBalance;

    @NotNull
    @Column(name = "estado")
    private Boolean status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private AccountClient accountClient;

    @Transient
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return getAccountNumber().equals(account.getAccountNumber()) && getAccountType().equals(account.getAccountType()) && getInitialBalance().equals(account.getInitialBalance()) && getStatus().equals(account.getStatus()) && getAccountClient().equals(account.getAccountClient()) && getClient().equals(account.getClient());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountNumber(), getAccountType(), getInitialBalance(), getStatus(), getAccountClient(), getClient());
    }
}
