package com.haulmont.creditSystem.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CREDIT_OFFER")
public class CreditOffer {

    @Column(name = "ID")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "CREDIT_SUM")
    private long creditSum;

   /* @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    */

   /* @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CREDIT_ID")
    private Credit credit;

    */

   /* @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PAYMENT_SCHEDULE_ID")
    */
    private List<PaymentSchedule> paymentSchedules;

    public CreditOffer(Client client, Credit credit, long creditSum, List<PaymentSchedule> paymentSchedules){
       // this.setClient(client);
       // this.setCredit(credit);
        this.setCreditSum(creditSum);
        this.setPaymentSchedules(paymentSchedules);
    }
    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public long getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(long creditSum) {
        this.creditSum = creditSum;
    }

   /* public Client getClient() {
        return client;
    }



    public void setClient(Client client) {
        this.client = client;
    }

    */

   /* public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    */

    public List<PaymentSchedule> getPaymentSchedules() {
        return paymentSchedules;
    }

    public void setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
        this.paymentSchedules = paymentSchedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditOffer that = (CreditOffer) o;
        return creditSum == that.creditSum &&
               // client.equals(that.client) &&
                //credit.equals(that.credit) &&
                paymentSchedules.equals(that.paymentSchedules);
    }
/*
    @Override
    public int hashCode() {
        return Objects.hash(creditSum, client, credit, paymentSchedules);
    }

 */
}
