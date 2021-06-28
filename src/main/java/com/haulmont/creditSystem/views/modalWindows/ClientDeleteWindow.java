package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.ClientService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ClientDeleteWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Client selectedClient;

    private BankService<Client> clientService;
    private BankService<CreditOffer> offerService;

    private TextField nameField = new TextField("Name:");
    private  TextField secondNameField = new TextField("Second name:");
    private  TextField patronymicField = new TextField("Patronymic:");
    private TextField phoneNumberField = new TextField("Phone number:");
    private TextField emailField = new TextField("Email:");
    private TextField passportIdField = new TextField("Passport ID:");

    private Label warningLbl = new Label("WARNING!");
    private Label firstWarningMessageLbl = new Label("By deleting a client, you also delete their credit offer!");
    private Label secondWarningMessageLbl = new Label("When you click ok, the data will be permanently deleted!");
    private Label confirmationLbl = new Label("Are you sure?");

    private Button okBtn = new com.vaadin.ui.Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new com.vaadin.ui.Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public ClientDeleteWindow(Client client){
        this.selectedClient = client;
        clientService = new ClientService();
        offerService = new CreditOfferService();
        setModal(true);
        setWidth("40%");
        setHeight("80%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        nameField.setValue(selectedClient.getName());
        secondNameField.setValue(selectedClient.getSecondName());
        patronymicField.setValue(selectedClient.getPatronymic());
        phoneNumberField.setValue(selectedClient.getPhoneNumber());
        emailField.setValue(selectedClient.getEmail());
        passportIdField.setValue(selectedClient.getPassportId());

        nameField.setReadOnly(true);
        secondNameField.setReadOnly(true);
        patronymicField.setReadOnly(true);
        phoneNumberField.setReadOnly(true);
        emailField.setReadOnly(true);
        passportIdField.setReadOnly(true);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(warningLbl, firstWarningMessageLbl,secondWarningMessageLbl, nameField, secondNameField, patronymicField, passportIdField, phoneNumberField, emailField, passportIdField, confirmationLbl, buttonsLayout);

        mainLayout.setComponentAlignment(warningLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(firstWarningMessageLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(secondWarningMessageLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(nameField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(secondNameField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(patronymicField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(phoneNumberField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(emailField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(passportIdField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(confirmationLbl, Alignment.BOTTOM_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);

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
                delete(selectedClient);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

    }

    private boolean delete(Client selectedClient){
        try{
            clientService.delete(selectedClient);
            Notification.show("Client and his credit offer successfully deleted");
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
