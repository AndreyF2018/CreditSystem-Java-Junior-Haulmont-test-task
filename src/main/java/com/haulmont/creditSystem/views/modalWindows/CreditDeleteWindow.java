package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.services.CreditService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CreditDeleteWindow extends Window{
    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Credit selectedCredit;

    private BankService<Credit> creditService;
    private BankService<CreditOffer> offerService;

    private TextField creditLimitField = new TextField("Credit limit:");
    private TextField interestRateField = new TextField("Interest rate:");

    private Label warningLbl = new Label("WARNING!");
    private Label warningMessageLbl = new Label("When you click ok, the data will be permanently deleted!");
    private Label confirmationLbl = new Label("Are you sure?");

    private Button okBtn = new com.vaadin.ui.Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new com.vaadin.ui.Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public CreditDeleteWindow(Credit credit){
        this.selectedCredit = credit;
        creditService = new CreditService();
        offerService = new CreditOfferService();
        setModal(true);
        setWidth("35%");
        setHeight("48%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        creditLimitField.setValue(String.valueOf(selectedCredit.getCreditLimit()));
        interestRateField.setValue(String.valueOf(selectedCredit.getInterestRate()));
        creditLimitField.setReadOnly(true);
        interestRateField.setReadOnly(true);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(warningLbl, warningMessageLbl, creditLimitField, interestRateField, confirmationLbl, buttonsLayout);

        mainLayout.setComponentAlignment(warningLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(warningMessageLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(creditLimitField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(interestRateField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(confirmationLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

        setContent(mainLayout);
    }

    private void setButtonsStyles(){
        okBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        cancelBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
    }

    private void setButtonsLogic(){
        okBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                delete(selectedCredit);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

    }

    private boolean delete(Credit selectedCredit){
        List<CreditOffer> offers = offerService.findAll();
        if (offers != null) {
            for (CreditOffer offer : offers) {
                if (offer.getCredit().equals(selectedCredit)) {
                    Notification.show("Deleting is not possible. There is already an offer with this credit");
                    return false;
                }
            }
        }
        try {
            creditService.delete(selectedCredit);
            Notification.show("Credit successfully deleted");
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
