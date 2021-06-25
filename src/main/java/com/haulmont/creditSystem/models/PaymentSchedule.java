package com.haulmont.creditSystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

//@Entity
//@Table(name = "PAYMENT_SCHEDULE")
public class PaymentSchedule {

   //@Column(name = "ID")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    //@Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    //@Column(name = "PAYMENT_SUM")
    private BigDecimal paymentSum; // paymentSum = loanBodySum + interestSum

    //@Column(name = "LOAN_BODY_SUM")
    private BigDecimal loanBodySum; //  loanBodySum = creditOffer.creditSum / months;
    // creditOffer.creditSum -= creditOffer.creditSum / months


   // @Column(name = "INTEREST_SUM")
    private BigDecimal interestSum; // interestSum = (creditOffer.creditSum * (credit.interestRate / 100)) / months
    // creditOffer.creditSum -= creditOffer.creditSum / months

   // @OneToOne(mappedBy = "paymentSchedules", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private CreditOffer creditOffer;

    public PaymentSchedule(LocalDate paymentDate, BigDecimal paymentSum, BigDecimal loanBodySum, BigDecimal interestSum){
        this.setPaymentDate(paymentDate);
        this.setPaymentSum(paymentSum);
        this.setLoanBodySum(loanBodySum);
        this.setInterestSum(interestSum);
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(BigDecimal paymentSum) {
        this.paymentSum = paymentSum;
    }

    public BigDecimal getLoanBodySum() {
        return loanBodySum;
    }

    public void setLoanBodySum(BigDecimal loanBodySum) {
        this.loanBodySum = loanBodySum;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public CreditOffer getCreditOffer() {
        return creditOffer;
    }

    public void setCreditOffer(CreditOffer creditOffer) {
        this.creditOffer = creditOffer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSchedule that = (PaymentSchedule) o;
        return paymentDate.equals(that.paymentDate) &&
                paymentSum.equals(that.paymentSum) &&
                loanBodySum.equals(that.loanBodySum) &&
                interestSum.equals(that.interestSum) &&
                creditOffer.equals(that.creditOffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentDate, paymentSum, loanBodySum, interestSum, creditOffer);
    }
}
