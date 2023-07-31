package com.co.technicaltest.neoris.account.models.entity;

import domain.models.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuentas_usuarios")
@Entity
public class AccountClient extends BaseEntity {

    @Column(name = "cliente_id")
    private Long clientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountClient that = (AccountClient) o;
        return getClientId().equals(that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClientId());
    }
}
