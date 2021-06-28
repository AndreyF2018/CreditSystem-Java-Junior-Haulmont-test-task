package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.models.PaymentSchedule;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.math.BigDecimal;
import java.util.List;

public class PaymentScheduleWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout resultSumsLayout = new HorizontalLayout();

    private Grid<PaymentSchedule> scheduleGrid = new Grid<>(PaymentSchedule.class);

    private CreditOffer selectedOffer;

    private int enteredMonths;

    private List<PaymentSchedule> paymentScheduleList;

    private TextField totalInterestSumField = new TextField("Total interest sum");
    private TextField totalPaymentSumField = new TextField("Total payment sum");

    private Button backBtn = new com.vaadin.ui.Button("Back", VaadinIcons.ARROW_BACKWARD);

    public PaymentScheduleWindow(CreditOffer offer, int months) {
        this.selectedOffer = offer;
        this.enteredMonths = months;
        this.paymentScheduleList = getPaymentSchedule(months);
        setModal(true);
        setSizeFull();
        scheduleGrid.setColumns("paymentDate", "loanBodySum", "interestSum", "paymentSum");
        scheduleGrid.setSizeFull();
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();

        //List<PaymentSchedule> paymentScheduleList = selectedOffer.getPaymentSchedule(enteredMonths);
        scheduleGrid.setItems(this.paymentScheduleList);

        BigDecimal totalInterestSum = getTotalInterestSum(enteredMonths);
        BigDecimal totalPaymentSum = getTotalPaymentSum(enteredMonths);

        totalInterestSumField.setValue(totalInterestSum.toString());
        totalPaymentSumField.setValue(totalPaymentSum.toString());

        totalInterestSumField.setReadOnly(true);
        totalPaymentSumField.setReadOnly(true);

        resultSumsLayout.addComponents(totalInterestSumField, totalPaymentSumField);

        mainLayout.addComponents(scheduleGrid, resultSumsLayout, backBtn);

        mainLayout.setComponentAlignment(backBtn, Alignment.TOP_CENTER);

        setContent(mainLayout);
    }

    private void setButtonsStyles(){
        backBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
    }

    private void setButtonsLogic(){
        backBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private List<PaymentSchedule> getPaymentSchedule(int months) {
        try {
            List<PaymentSchedule> paymentScheduleList = selectedOffer.getPaymentSchedule(months);
            return paymentScheduleList;
        }
        catch (Exception e) {
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    private BigDecimal getTotalInterestSum (int months){
        BigDecimal totalInterestSum = BigDecimal.valueOf(0);
        try {
            //List<PaymentSchedule> paymentScheduleList = selectedOffer.getPaymentSchedule(months);
            for (int i = 0; i < months; i ++) {
                totalInterestSum = totalInterestSum.add(this.paymentScheduleList.get(i).getInterestSum());
            }
        }
        catch (Exception e) {
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }
        return totalInterestSum;
    }

    private BigDecimal getTotalPaymentSum (int months){
        BigDecimal totalPaymentSum = BigDecimal.valueOf(0);
        try {
            //List<PaymentSchedule> paymentScheduleList = selectedOffer.getPaymentSchedule(months);
            for (int i = 0; i < months; i ++) {
                totalPaymentSum = totalPaymentSum.add(this.paymentScheduleList.get(i).getPaymentSum());
            }
        }
        catch (Exception e) {
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }
        return totalPaymentSum;
    }
}
