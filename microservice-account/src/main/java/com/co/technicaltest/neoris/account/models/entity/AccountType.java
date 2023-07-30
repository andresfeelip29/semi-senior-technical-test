package com.co.technicaltest.neoris.account.models.entity;

import domain.models.entity.BaseEntity;
import domain.models.enums.BankAccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipo_cuenta")
@Entity
public class AccountType extends BaseEntity {

    @NotNull
    @Column(name = "nombre_tipo_cuenta")
    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountType that = (AccountType) o;
        return getBankAccountType() == that.getBankAccountType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBankAccountType());
    }
}
