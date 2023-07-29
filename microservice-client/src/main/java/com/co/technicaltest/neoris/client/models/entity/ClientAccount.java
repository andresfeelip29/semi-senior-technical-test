package com.co.technicaltest.neoris.client.models.entity;

import domain.models.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario_cuenta")
@Entity
public class ClientAccount extends BaseEntity {

    @Column(name = "cuenta_id")
    private Long accountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientAccount that = (ClientAccount) o;
        return getAccountId().equals(that.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountId());
    }
}
