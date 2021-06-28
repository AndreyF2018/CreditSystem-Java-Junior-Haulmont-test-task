package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.services.CreditService;
import com.haulmont.creditSystem.utils.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CreditEditWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Credit selectedCredit;

    private BankService<Credit> creditService;
    private BankService<CreditOffer> offerService;

    private TextField creditLimitField = new TextField("New credit limit*:");
    private TextField interestRateField = new TextField("New interest rate*:");

    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public CreditEditWindow(Credit credit){
        this.selectedCredit = credit;
        creditService = new CreditService();
        offerService = new CreditOfferService();
        setModal(true);
        setWidth("17%");
        setHeight("35%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        creditLimitField.setValue(String.valueOf(selectedCredit.getCreditLimit()));
        interestRateField.setValue(String.valueOf(selectedCredit.getInterestRate()));

        creditLimitField.setMaxLength(12);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(creditLimitField, interestRateField, buttonsLayout);

        mainLayout.setComponentAlignment(creditLimitField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(interestRateField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

        setContent(mainLayout);
    }

    private void setButtonsStyles(){
        okBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        cancelBtn.addStyleName(ValoTheme.BUTTON_DANGER);
    }

    private void setButtonsLogic(){
        okBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                update(selectedCredit);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

    }

    private boolean update(Credit selectedCredit){
        if (!Validator.isNotNullFields(creditLimitField, interestRateField)){
            Notification.show("Fields marked with a symbol (*) are required");
            return false;
        }
        if(!(Validator.isDigitType(creditLimitField.getValue()))){
            Notification.show("Invalid symbols in the credit limit");
            return false;
        }
        long creditLimit = Long.parseLong(creditLimitField.getValue());
        if (creditLimit < 10000) {
            Notification.show("Minimum credit limit = 10000");
            return false;
        }
        List<CreditOffer> offers = offerService.findAll();
        if (offers != null) {
            for (CreditOffer offer : offers) {
                if (offer.getCredit().equals(selectedCredit) && creditLimit< offer.getCreditSum()) {
                    Notification.show("The update is not possible. The new credit limit is less than the credit sum in the corresponding credit offer.");
                    return false;
                }
            }
        }
        double interestRate = 0;
        try {
            interestRate = Double.parseDouble(interestRateField.getValue());
        }
        catch (Exception e){
            Notification.show("Invalid symbols in the interest rate");
            e.printStackTrace();
            return false;
        }

        if (interestRate < 1 || interestRate > 100){
            Notification.show("Incorrect value of interest rate");
            return false;
        }

        try {
            selectedCredit.setCreditLimit(creditLimit);
            selectedCredit.setInterestRate(interestRate);

            creditService.update(selectedCredit);
            Notification.show("Credit's data has been successfully updated");
            close();
            return true;
        }
        catch (Exception e){
            Notification.show("Something went wrong. " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
