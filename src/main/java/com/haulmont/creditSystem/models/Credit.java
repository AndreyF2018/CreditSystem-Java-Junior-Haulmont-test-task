package com.haulmont.creditSystem.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CREDITS")
public class Credit {

    @Column(name = "ID")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "CREDIT_LIMIT")
    private long creditLimit;

    @Column(name = "INTEREST_RATE")
    private double interestRate;

    //@OneToOne(mappedBy = "credit", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    //private List<CreditOffer> creditOffers;

    public Credit(){

    }
    public Credit(long creditLimit, double interestRate){
        this.setCreditLimit(creditLimit);
        this.setInterestRate(interestRate);
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(long creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return creditLimit == credit.creditLimit &&
                Double.compare(credit.interestRate, interestRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditLimit, interestRate);
    }

    @Override
    public String toString() {
        return "Interest rate = " + interestRate +
                ", credit limit =" + creditLimit;
    }
}
