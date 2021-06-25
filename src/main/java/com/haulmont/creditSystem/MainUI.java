package com.haulmont.creditSystem;

import com.haulmont.creditSystem.views.BankView;
import com.haulmont.creditSystem.views.ClientView;
import com.haulmont.creditSystem.views.CreditView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addTab(new BankView(), "Bank");
        verticalLayout.addComponent(tabSheet);
        setContent(verticalLayout);
    }
}
