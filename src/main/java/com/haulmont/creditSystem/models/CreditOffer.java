package com.haulmont.creditSystem.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CREDIT_ID")
    private Credit credit;

    public CreditOffer(){

    }

    public CreditOffer(Client client, Credit credit, long creditSum){
        this.setClient(client);
        this.setCredit(credit);
        this.setCreditSum(creditSum);
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    //Calculation of the payment schedule
    public List<PaymentSchedule> getPaymentSchedule(int months) {
        //Initializing initial data
        List<PaymentSchedule> paymentScheduleList = new ArrayList<PaymentSchedule>(months);
        PaymentSchedule paymentSchedule = new PaymentSchedule();
        paymentScheduleList.add(paymentSchedule);
        LocalDate paymentDate = LocalDate.now();
        //paymentScheduleList.get(0).setPaymentDate(paymentDate);
        BigDecimal curCreditSum = BigDecimal.valueOf(this.creditSum);
        BigDecimal interestRate = BigDecimal.valueOf(this.getCredit().getInterestRate()).divide(BigDecimal.valueOf(100));
        //Calculation
        for (int i = 0; i < months; i++){
            PaymentSchedule tempPaymentSchedule = new PaymentSchedule();
            paymentScheduleList.add(tempPaymentSchedule);
            paymentDate = paymentDate.plusMonths(1);
            //LocalDate paymentDate = paymentScheduleList.get(i).getPaymentDate().plusMonths(1);
            BigDecimal loanBodySum = BigDecimal.valueOf(this.getCreditSum()).divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
            BigDecimal interestSum = (curCreditSum.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP)).multiply(interestRate).setScale(2, RoundingMode.HALF_UP);;
            BigDecimal paymentSum = loanBodySum.add(interestSum).setScale(2, RoundingMode.HALF_UP);
            curCreditSum = curCreditSum.subtract(loanBodySum);

            paymentScheduleList.get(i).setPaymentDate(paymentDate);
            paymentScheduleList.get(i).setLoanBodySum(loanBodySum);
            paymentScheduleList.get(i).setInterestSum(interestSum);
            paymentScheduleList.get(i).setPaymentSum(paymentSum);
        }
        return  paymentScheduleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditOffer that = (CreditOffer) o;
        return creditSum == that.creditSum &&
                client.equals(that.client) &&
                credit.equals(that.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditSum, client, credit);
    }
}
