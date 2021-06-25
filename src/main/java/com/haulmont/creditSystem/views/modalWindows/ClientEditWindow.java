package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.services.ClientService;
import com.haulmont.creditSystem.services.IService;
import com.haulmont.creditSystem.utils.Validator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ClientEditWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Client selectedClient;

    private IService<Client> clientIService;

    private TextField nameField = new TextField("New name*:");
    private  TextField secondNameField = new TextField("New second name*:");
    private  TextField patronymicField = new TextField("New patronymic:");
    private TextField phoneNumberField = new TextField("New phone number*:");
    private TextField emailField = new TextField("New email*:");
    private TextField passportIdField = new TextField("New passport ID*:");

    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public ClientEditWindow(Client client){
        this.selectedClient = client;
        clientIService = new ClientService();
        setModal(true);
        setWidth("25%");
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

        phoneNumberField.setMaxLength(11);
        passportIdField.setMaxLength(10);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(nameField, secondNameField, patronymicField, passportIdField, phoneNumberField, emailField, passportIdField, buttonsLayout);

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
                update(selectedClient);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });

    }

    private boolean update(Client selectedClient){
        if (!Validator.isNotNullFields(nameField, secondNameField, phoneNumberField, emailField, passportIdField)){
            Notification.show("Fields marked with a symbol (*) are required");
            return false;
        }
        if(!(Validator.isDigitType(phoneNumberField.getValue()) && Validator.isDigitType(passportIdField.getValue()))){
            Notification.show("Invalid numeric characters");
            return false;
        }
        if (! (Validator.isLetterType(nameField.getValue()) && Validator.isLetterType(secondNameField.getValue()) && (patronymicField.getValue().equals("") || Validator.isLetterType(patronymicField.getValue())  ))){
            Notification.show("Invalid characters in full name");
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
            selectedClient.setName(name);
            selectedClient.setSecondName(secondName);
            selectedClient.setPatronymic(patronymic);
            selectedClient.setPhoneNumber(phoneNumber);
            selectedClient.setEmail(email);
            selectedClient.setPassportId(passportId);

            clientIService.update(selectedClient);
            Notification.show("Client's data has been successfully updated");
            return true;
        }
        catch (Exception e) {
            Notification.show("Something went wrong. " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

}
