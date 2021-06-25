package com.haulmont.creditSystem.models;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import com.sun.istack.Nullable;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Column(name = "ID")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SECOND_NAME")
    private String secondName;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSPORT_ID")
    private String passportId;

    /*
    @OneToOne (mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private CreditOffer creditOffer;
*/

    public UUID getId() {
        return id;
    }

    public Client(){

    }
    public Client(String name, String secondName, @Nullable String patronymic, String phoneNumber, String email, String passportId) {
        this.setName(name);
        this.setSecondName(secondName);
        this.setPatronymic(patronymic);
        this.setPhoneNumber(phoneNumber);
        this.setEmail(email);
        this.setPassportId(passportId);
    }
    private void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return name.equals(client.name) &&
                secondName.equals(client.secondName) &&
                Objects.equals(patronymic, client.patronymic) &&
                phoneNumber.equals(client.phoneNumber) &&
                email.equals(client.email) &&
                passportId.equals(client.passportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, secondName, patronymic, phoneNumber, email, passportId);
    }
}
