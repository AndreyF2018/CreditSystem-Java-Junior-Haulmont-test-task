package com.haulmont.creditSystem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PaymentSchedule {

    private LocalDate paymentDate;

    private BigDecimal paymentSum;

    private BigDecimal loanBodySum;

    private BigDecimal interestSum;

    public PaymentSchedule() {

    }

    public PaymentSchedule(LocalDate paymentDate, BigDecimal paymentSum, BigDecimal loanBodySum, BigDecimal interestSum){
        this.setPaymentDate(paymentDate);
        this.setPaymentSum(paymentSum);
        this.setLoanBodySum(loanBodySum);
        this.setInterestSum(interestSum);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSchedule that = (PaymentSchedule) o;
        return paymentDate.equals(that.paymentDate) &&
                paymentSum.equals(that.paymentSum) &&
                loanBodySum.equals(that.loanBodySum) &&
                interestSum.equals(that.interestSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentDate, paymentSum, loanBodySum, interestSum);
    }
}
