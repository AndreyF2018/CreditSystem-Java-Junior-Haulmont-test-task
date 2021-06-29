package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.services.CreditService;
import com.haulmont.creditSystem.utils.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CreditOfferEditWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private CreditOffer selectedOffer;
    private Client selectedClient;
    private Credit selectedCredit;

    private BankService<CreditOffer> offerService;
    private BankService<Credit> creditService;

    private TextField creditSumField = new TextField("New credit sum*:");

    private ComboBox<Credit> creditBox = new ComboBox<>("New credit*:");

    private Button updateClientBtn = new Button ("Edit a client", VaadinIcons.USER_CARD);
    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public CreditOfferEditWindow(CreditOffer offer){
        this.selectedOffer = offer;
        this.selectedClient = selectedOffer.getClient();
        this.selectedCredit = selectedOffer.getCredit();
        offerService = new CreditOfferService();
        creditService = new CreditService();
        setModal(true);
        setWidth("30%");
        setHeight("40%");
        addAllComponents();
        setButtonsLogic();
    }

    private  void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        creditSumField.setValue(String.valueOf(selectedOffer.getCreditSum()));
        creditBox.setItems(creditService.findAll());
        creditBox.setValue(selectedCredit);
        buttonsLayout.addComponents(okBtn, cancelBtn);

        creditSumField.setMaxLength(12);

        mainLayout.addComponents(creditSumField, creditBox, updateClientBtn, buttonsLayout);

        mainLayout.setComponentAlignment(creditSumField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(creditBox, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(updateClientBtn, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

        setContent(mainLayout);
    }

    private void setButtonsStyles(){
        updateClientBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        okBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        cancelBtn.addStyleName(ValoTheme.BUTTON_DANGER);
    }

    private void setButtonsLogic(){
        updateClientBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().addWindow(new ClientEditWindow(selectedClient));
            }
        });
        okBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                update(selectedOffer);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private boolean update(CreditOffer selectedOffer){
        if (!Validator.isNotNullFields(creditSumField) || creditBox.getValue() == null){
            Notification.show("Fields marked with a symbol (*) are required");
            return false;
        }

        if (!Validator.isDigitType(creditSumField.getValue())){
            Notification.show("Invalid symbols in the credit sum");
            return false;
        }

        try {
            long creditSum = Long.parseLong(creditSumField.getValue());
            Credit credit = creditBox.getValue();
            if(creditSum > credit.getCreditLimit()){
                Notification.show("The new credit sum is greater than the credit limit in the selected credit.");
                return false;
            }
            selectedOffer.setCreditSum(creditSum);
            selectedOffer.setCredit(credit);
            offerService.update(selectedOffer);
            Notification.show("Credit offer updated successfully!");
            close();
        }
        catch (Exception e){
            Notification.show("Something went wrong. " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
