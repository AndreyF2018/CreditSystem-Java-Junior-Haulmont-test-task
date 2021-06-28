package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.ClientService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CreditOfferDeleteWindow extends Window {
    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private CreditOffer selectedOffer;
    private Client selectedClient;
    private Credit selectedCredit;

    private BankService<CreditOffer> offerService;
    private BankService<Client> clientService;

    private TextField clientField = new TextField("Client:");
    private TextField creditField = new TextField("Credit:");
    private TextField creditSumField = new TextField("Credit sum:");

    private Label warningLbl = new Label("WARNING!");
    private Label firstWarningMessageLbl = new Label("By deleting a credit offer, you also delete the corresponding client!");
    private Label secondWarningMessageLbl = new Label("When you click ok, the data will be permanently deleted!");
    private Label confirmationLbl = new Label("Are you sure?");

    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public CreditOfferDeleteWindow(CreditOffer offer){
        this.selectedOffer = offer;
        this.selectedClient = selectedOffer.getClient();
        this.selectedCredit = selectedOffer.getCredit();
        offerService = new CreditOfferService();
        //clientService = new ClientService();
        setModal(true);
        setWidth("40%");
        setHeight("65%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        clientField.setValue(selectedClient.toString());
        creditField.setValue(selectedCredit.toString());
        creditSumField.setValue(String.valueOf(selectedOffer.getCreditSum()));
        clientField.setReadOnly(true);
        creditField.setReadOnly(true);
        creditSumField.setReadOnly(true);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(warningLbl, firstWarningMessageLbl, secondWarningMessageLbl, clientField, creditField, creditSumField, confirmationLbl, buttonsLayout);

        mainLayout.setComponentAlignment(warningLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(firstWarningMessageLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(secondWarningMessageLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(clientField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(creditField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(creditSumField, Alignment.TOP_CENTER);
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
                delete(selectedOffer);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

    }

    private boolean delete (CreditOffer selectedOffer) {
        try {
            offerService.delete(selectedOffer);
            clientService = new ClientService();
            clientService.delete(selectedClient);
            Notification.show("Credit offer and corresponding  client successfully deleted");
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
