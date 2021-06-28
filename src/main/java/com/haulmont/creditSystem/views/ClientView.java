package com.haulmont.creditSystem.views;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.services.ClientService;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.views.modalWindows.ClientDeleteWindow;
import com.haulmont.creditSystem.views.modalWindows.ClientEditWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class ClientView extends VerticalLayout {

    private BankService<Client> clientService;

    private Grid<Client> clientGrid = new Grid<>(Client.class);

    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Button editBtn = new Button("Edit", VaadinIcons.EDIT);
    private Button deleteBtn = new Button("Delete", VaadinIcons.CLOSE);
    private Button refreshListBtn = new Button("Refresh List", VaadinIcons.REFRESH);


    public ClientView() {
        clientService = new ClientService();
        clientGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        setSizeFull();
        setSpacing(true);
        clientGrid.setColumns("name", "secondName", "patronymic", "phoneNumber", "email", "passportId");
        clientGrid.setSizeFull();
        findAll();
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents() {
        setButtonsStyles();
        buttonsLayout.addComponents(editBtn, deleteBtn);
        addComponents(clientGrid, buttonsLayout, refreshListBtn);
        setComponentAlignment(refreshListBtn, Alignment.BOTTOM_CENTER);
    }

    private void setButtonsStyles() {
        editBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        refreshListBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private void setButtonsLogic(){
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Client selectedClient = null;
                for(Client item : clientGrid.getSelectedItems()){
                    selectedClient = item;
                }
                if(selectedClient != null) {
                    openEditWindow(selectedClient);
                }
                else {
                    Notification.show("Please select a client");
                }

            }
        });
        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Client selectedClient = null;
                for(Client item : clientGrid.getSelectedItems()){
                    selectedClient = item;
                }
                if(selectedClient != null) {
                    openDeleteWindow(selectedClient);
                }
                else {
                    Notification.show("Please select a client");
                }
            }
        });
        refreshListBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                findAll();
            }
        });
    }

    private void openEditWindow(Client selectedClient) {
        UI.getCurrent().addWindow(new ClientEditWindow(selectedClient));
    }

    private void openDeleteWindow(Client selectedClient){
        UI.getCurrent().addWindow(new ClientDeleteWindow(selectedClient));
    }
    private void findAll() {
        try {
            List<Client> clients = clientService.findAll();
            clientGrid.setItems(clients);
            clientGrid.getDataProvider().refreshAll();
        }
        catch (Exception e){
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }

    }
}
