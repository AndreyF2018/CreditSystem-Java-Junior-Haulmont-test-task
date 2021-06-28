package com.haulmont.creditSystem.views.modalWindows;

import com.haulmont.creditSystem.models.CreditOffer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class InputMonthsWindow extends Window {

    private VerticalLayout mainLayout = new VerticalLayout();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private CreditOffer selectedOffer;
    private TextField monthsField = new TextField("Enter the number of months:");

    private Button okBtn = new Button("Ok", VaadinIcons.CHECK);
    private Button cancelBtn = new Button("Cancel", VaadinIcons.CLOSE_CIRCLE);

    public InputMonthsWindow(CreditOffer offer){
        this.selectedOffer = offer;
        setModal(true);
        setWidth("30%");
        setHeight("25%");
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        mainLayout.setSpacing(true);

        buttonsLayout.addComponents(okBtn, cancelBtn);

        mainLayout.addComponents(monthsField, buttonsLayout);

        mainLayout.setComponentAlignment(monthsField, Alignment.TOP_CENTER);
        mainLayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

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
                openPaymentScheduleWindow(selectedOffer);
            }
        });
        cancelBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
    }

    private boolean openPaymentScheduleWindow(CreditOffer selectedOffer) {
        try {
            int months = Integer.parseInt(monthsField.getValue());
            if (months > 480) {
                Notification.show("Too many number of months");
                return false;
            }
            if (months < 1) {
                Notification.show("The number of months must be positive");
                return false;
            }
            UI.getCurrent().addWindow(new PaymentScheduleWindow(selectedOffer, months));
            close();

        }
        catch (Exception e) {
            Notification.show("Incorrect months value");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

