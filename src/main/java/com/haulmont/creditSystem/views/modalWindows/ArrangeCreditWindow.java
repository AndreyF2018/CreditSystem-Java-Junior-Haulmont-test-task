package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.ClientService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.utils.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class ArrangeCreditWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Credit selectedCredit;

    private BankService<Client> clientService;
    private BankService<CreditOffer> creditOfferService;

    private Label infoLbl = new Label("Enter your data");

    private TextField creditSumField = new TextField("Credit sum*:");
    private TextField nameField = new TextField("Your name*:");
    private  TextField secondNameField = new TextField("Your second name*:");
    private  TextField patronymicField = new TextField("Your patronymic:");
    private TextField phoneNumberField = new TextField("Your phone number*:");
    private TextField emailField = new TextField("Your email*:");
    private TextField passportIdField = new TextField("Your passport ID*:");

    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public ArrangeCreditWindow(Credit credit){
        this.selectedCredit = credit;
        clientService = new ClientService();
        creditOfferService = new CreditOfferService();
        setModal(true);
        setWidth("25%");
        setHeight("90%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        creditSumField.setMaxLength(12);
        phoneNumberField.setMaxLength(11);
        passportIdField.setMaxLength(10);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(infoLbl, creditSumField, nameField, secondNameField, patronymicField, passportIdField, phoneNumberField, emailField, passportIdField, buttonsLayout);

        mainLayout.setComponentAlignment(infoLbl, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(creditSumField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(nameField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(secondNameField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(patronymicField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(phoneNumberField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(emailField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(passportIdField, Alignment.TOP_CENTER);
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
                add(selectedCredit);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private boolean add (Credit selectedCredit){
        if (!Validator.isNotNullFields(creditSumField, nameField, secondNameField, phoneNumberField, emailField, passportIdField)){
            Notification.show("Fields marked with a symbol (*) are required");
            return false;
        }
        if(!(Validator.isDigitType(creditSumField.getValue()) && Validator.isDigitType(phoneNumberField.getValue()) && Validator.isDigitType(passportIdField.getValue()))){
            Notification.show("Invalid numeric symbols");
            return false;
        }

        if (Long.parseLong(creditSumField.getValue()) <= 0){
            Notification.show("The credit sum must be positive");
            return false;
        }

        if (Long.parseLong(creditSumField.getValue()) > selectedCredit.getCreditLimit()){
            Notification.show("The credit sum must not be higher than the credit limit");
            return false;
        }

        if (!(Validator.isLetterType(nameField.getValue()) && Validator.isLetterType(secondNameField.getValue()) && (patronymicField.getValue().equals("") || Validator.isLetterType(patronymicField.getValue())  ))){
            Notification.show("Invalid symbols in full name");
            return false;
        }
        // Capitalize the first letters of the first name, second name and patronymic, and cursive the remaining letters:
        String name = nameField.getValue().substring(0, 1).toUpperCase() + nameField.getValue().substring(1).toLowerCase();
        String secondName = secondNameField.getValue().substring(0, 1).toUpperCase() + secondNameField.getValue().substring(1).toLowerCase();
        String patronymic = patronymicField.getValue().equals("")  ? "" : patronymicField.getValue().substring(0, 1).toUpperCase() + patronymicField.getValue().substring(1).toLowerCase();;
        String phoneNumber = phoneNumberField.getValue();
        String email = emailField.getValue();
        String passportId = passportIdField.getValue();

        try {
            Client client = new Client(name, secondName, patronymic, phoneNumber, email, passportId);
            List<Client> allClients = clientService.findAll();
            for(Client item : allClients){
                if (client.equals(item)) {
                    Notification.show("This client already exists");
                    return false;
                }
            }
            clientService.create(client);

            long creditSum = Long.parseLong(creditSumField.getValue());
            CreditOffer creditOffer = new CreditOffer(client, selectedCredit, creditSum);
            creditOfferService.create(creditOffer);

            Notification.show("Credit offer and client added successfully");
            close();
            return true;
        }
        catch (Exception e) {
            Notification.show("Something went wrong. " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
