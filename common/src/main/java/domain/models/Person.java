package domain.models;

import domain.models.entity.BaseEntity;
import domain.models.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@MappedSuperclass
public abstract class Person<ID> extends BaseEntity<ID> {

    @NotNull
    @Column(name = "nombres")
    protected String name;

    @NotNull
    @Column(name = "genero")
    @Enumerated(EnumType.STRING)
    protected Gender gender;

    @NotNull
    @Column(name = "edad")
    protected Integer age;

    @NotNull
    @Column(name = "indentificacion")
    protected String identification;

    @NotNull
    @Column(name = "direccion_residencia")
    protected String address;

    @NotNull
    @Column(name = "numero_telefono")
    protected String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Person<?> person = (Person<?>) o;
        return getName().equals(person.getName()) && getGender() == person.getGender() && getAge().equals(person.getAge()) && getIdentification().equals(person.getIdentification()) && getAddress().equals(person.getAddress()) && getPhone().equals(person.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getGender(), getAge(), getIdentification(), getAddress(), getPhone());
    }
}
