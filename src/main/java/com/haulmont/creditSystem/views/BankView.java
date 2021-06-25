package com.haulmont.creditSystem.views;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class BankView extends VerticalLayout {

    private TabSheet tabSheet;
    public BankView(){
        tabSheet = new TabSheet();
        tabSheet.addTab(new CreditView(), "Credits");
        tabSheet.addTab(new ClientView(), "Clients");
        addComponent(tabSheet);
    }
}
