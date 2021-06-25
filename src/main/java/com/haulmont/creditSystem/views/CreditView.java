package com.haulmont.creditSystem.views;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.services.CreditService;
import com.haulmont.creditSystem.services.IService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.hibernate.internal.util.collections.IdentitySet;

import java.util.List;

public class CreditView extends VerticalLayout {

    private IService<Credit> creditService;

    private Grid<Credit> creditGrid = new Grid<>(Credit.class);

    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Button arrangeCreditBtn = new Button("Arrange credit", VaadinIcons.DOLLAR);
    private Button editBtn = new Button("Edit", VaadinIcons.EDIT);
    private Button deleteBtn = new Button("Delete", VaadinIcons.CLOSE);
    private Button refreshListBtn = new Button("Refresh List", VaadinIcons.REFRESH);

    public CreditView () {
        creditService = new CreditService();
        creditGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        setSizeFull();
        setSpacing(true);
        creditGrid.setColumns("creditLimit", "interestRate");
        creditGrid.setSizeFull();
        findAll();
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        buttonsLayout.addComponents(arrangeCreditBtn, editBtn, deleteBtn);
        addComponents(creditGrid, buttonsLayout, refreshListBtn);
        setComponentAlignment(refreshListBtn, Alignment.BOTTOM_CENTER);
    }

    private void setButtonsStyles(){
        arrangeCreditBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        refreshListBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private void setButtonsLogic() {
        arrangeCreditBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });

        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });

        deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });

        refreshListBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                findAll();
            }
        });
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
