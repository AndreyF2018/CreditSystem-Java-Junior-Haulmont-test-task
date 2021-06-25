package com.haulmont.creditSystem.models;

import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BANK")
public class Bank {
    @Column(name = "ID")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

   /* @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CLIENT_ID")
    */
    private List<Client> clients;

    /*
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CREDIT_ID")
    */
    private List<Credit> credits;

    public Bank(){

    }

    public Bank(List<Client> clients, List<Credit> credits){
        this.setClients(clients);
        this.setCredits(credits);
    }



    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }
}
