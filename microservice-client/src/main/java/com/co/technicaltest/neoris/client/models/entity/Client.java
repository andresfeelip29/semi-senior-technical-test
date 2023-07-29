package com.co.technicaltest.neoris.client.models.entity;

import com.co.technicaltest.neoris.client.models.Account;
import domain.models.Person;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Entity
public class Client extends Person<Long> {

    @Column(name = "contraseña")
    private String password;

    @Column(name = "estado")
    private Boolean status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cuenta_id")
    private List<ClientAccount> clientAccounts;

    @Transient
    private List<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return getPassword().equals(client.getPassword()) && getStatus().equals(client.getStatus()) && getClientAccounts().equals(client.getClientAccounts()) && getAccounts().equals(client.getAccounts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPassword(), getStatus(), getClientAccounts(), getAccounts());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("password='").append(password).append('\'');
        sb.append(", status=").append(status);
        sb.append(", clientAccounts=").append(clientAccounts);
        sb.append(", accounts=").append(accounts);
        sb.append(", name='").append(name).append('\'');
        sb.append(", gender=").append(gender);
        sb.append(", age=").append(age);
        sb.append(", identification='").append(identification).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
