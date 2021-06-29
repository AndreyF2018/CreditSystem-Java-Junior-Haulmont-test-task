package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditService;
import com.haulmont.creditSystem.utils.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CreditAddWindow extends Window {
    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private BankService<Credit> creditService;

    private TextField creditLimitField = new com.vaadin.ui.TextField("Credit limit*:");
    private TextField interestRateField = new com.vaadin.ui.TextField("Interest rate*:");

    private Button okBtn = new com.vaadin.ui.Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new com.vaadin.ui.Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public CreditAddWindow(){
        creditService = new CreditService();
        setModal(true);
        setWidth("17%");
        setHeight("35%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

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
                add();
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private boolean add(){
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
            Credit credit = new Credit(creditLimit, interestRate);
            List<Credit> allCredits = creditService.findAll();
            for(Credit item : allCredits){
                if (credit.equals(item)){
                    Notification.show("This credit already exists");
                    return false;
                }
            }
            creditService.create(credit);
            Notification.show("Credit added successfully");
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
