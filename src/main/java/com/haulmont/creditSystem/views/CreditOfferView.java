package com.haulmont.creditSystem.views;

import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.views.modalWindows.CreditOfferEditWindow;
import com.haulmont.creditSystem.views.modalWindows.InputMonthsWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class CreditOfferView extends VerticalLayout {

    private BankService<CreditOffer> offerService;

    private Grid<CreditOffer> offerGrid = new Grid<>(CreditOffer.class);

    private HorizontalLayout buttonsLayout = new HorizontalLayout();

    private Button scheduleBtn = new Button("Payment schedule", VaadinIcons.CALENDAR_CLOCK);
    Button editBtn = new Button("Edit", VaadinIcons.EDIT);
    private Button refreshListBtn = new Button("Refresh List", VaadinIcons.REFRESH);

    public CreditOfferView(){
        offerService = new CreditOfferService();
        setSizeFull();
        setSpacing(true);
        offerGrid.setColumns("client", "credit", "creditSum");
        offerGrid.setSizeFull();
        findAll();
        addAllComponents();
        setButtonsLogic();
    }

    private void addAllComponents(){
        setButtonsStyles();
        buttonsLayout.addComponents(scheduleBtn, editBtn); //deleteBtn);
        addComponents(offerGrid, buttonsLayout, refreshListBtn);
        setComponentAlignment(refreshListBtn, Alignment.BOTTOM_CENTER);
    }

    private void setButtonsStyles(){
        scheduleBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        editBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        refreshListBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private void setButtonsLogic(){
        scheduleBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CreditOffer selectedOffer = null;
                for(CreditOffer item : offerGrid.getSelectedItems()){
                    selectedOffer = item;
                }
                if(selectedOffer != null) {
                    openInputMonthsWindow(selectedOffer);
                }
                else {
                    Notification.show("Please select a credit offer");
                }
            }
        });
        editBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CreditOffer selectedOffer = null;
                for(CreditOffer item : offerGrid.getSelectedItems()){
                    selectedOffer = item;
                }
                if(selectedOffer != null) {
                    openEditWindow(selectedOffer);
                }
                else {
                    Notification.show("Please select a credit offer");
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

    private void openInputMonthsWindow(CreditOffer selectedOffer){
        UI.getCurrent().addWindow(new InputMonthsWindow(selectedOffer));
    }

    private void openEditWindow(CreditOffer selectedOffer){
        UI.getCurrent().addWindow(new CreditOfferEditWindow(selectedOffer));
    }

    private void findAll(){
        try{
            List<CreditOffer> offers = offerService.findAll();
            offerGrid.setItems(offers);
            offerGrid.getDataProvider().refreshAll();
        }
        catch (Exception e){
            Notification.show("Something went wrong " + e.getMessage());
            e.printStackTrace();
        }
    }
}
