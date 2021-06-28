package com.haulmont.creditSystem.views;

import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.services.CreditService;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.views.modalWindows.ArrangeCreditWindow;
import com.haulmont.creditSystem.views.modalWindows.CreditAddWindow;
import com.haulmont.creditSystem.views.modalWindows.CreditDeleteWindow;
import com.haulmont.creditSystem.views.modalWindows.CreditEditWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CreditView extends VerticalLayout {

    private BankService<Credit> creditService;

    private Grid<Credit> creditGrid = new Grid<>(Credit.class);

    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Button arrangeCreditBtn = new Button("Arrange credit", VaadinIcons.DOLLAR);
    private Button addBtn = new Button("Add credit", VaadinIcons.CREDIT_CARD);
    private Button editBtn = new Button("Edit", VaadinIcons.EDIT);
    private Button deleteBtn = new Button("Delete", VaadinIcons.CLOSE);
    private Button refreshListBtn = new Button("Refresh List", VaadinIcons.REFRESH);

    public CreditView () {
        creditService = new CreditService();
        setSizeFull();
        setSpacing(true);
        creditGrid.setColumns("creditLimit", "interestRate");
        creditGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        creditGrid.setSizeFull();
        findAll();
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        buttonsLayout.addComponents(arrangeCreditBtn, addBtn, editBtn, deleteBtn);
        addComponents(creditGrid, buttonsLayout, refreshListBtn);
        setComponentAlignment(refreshListBtn, Alignment.BOTTOM_CENTER);
    }

    private void setButtonsStyles(){
        arrangeCreditBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        refreshListBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private void setButtonsLogic() {
        arrangeCreditBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Credit selectedCredit = null;
                for(Credit item : creditGrid.getSelectedItems()){
                    selectedCredit = item;
                }
                if(selectedCredit != null) {
                    openArrangeCreditWindow(selectedCredit);
                }
                else {
                    Notification.show("Please select a credit");
                }
            }
        });

        addBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                openAddWindow();
            }
        });

        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Credit selectedCredit = null;
                for(Credit item : creditGrid.getSelectedItems()){
                    selectedCredit = item;
                }
                if(selectedCredit != null) {
                    openEditWindow(selectedCredit);
                }
                else {
                    Notification.show("Please select a credit");
                }
            }
        });

        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Credit selectedCredit = null;
                for(Credit item : creditGrid.getSelectedItems()){
                    selectedCredit = item;
                }
                if(selectedCredit != null) {
                    openDeleteWindow(selectedCredit);
                }
                else {
                    Notification.show("Please select a credit");
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

    private void openArrangeCreditWindow(Credit selectedCredit){
        UI.getCurrent().addWindow(new ArrangeCreditWindow(selectedCredit));
    }
    private void openAddWindow(){
        UI.getCurrent().addWindow(new CreditAddWindow());
    }

    private void openEditWindow(Credit selectedCredit) {
        UI.getCurrent().addWindow(new CreditEditWindow(selectedCredit));
    }

    private void openDeleteWindow(Credit selectedCredit){
        UI.getCurrent().addWindow(new CreditDeleteWindow(selectedCredit));
    }

    private void findAll(){
        try {
            List<Credit> credits = creditService.findAll();
            creditGrid.setItems(credits);
            creditGrid.getDataProvider().refreshAll();
        }
        catch (Exception e){
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }
    }
}
