package com.haulmont.creditSystem.views;

import com.haulmont.creditSystem.models.Client;
import com.haulmont.creditSystem.models.Credit;
import com.haulmont.creditSystem.models.CreditOffer;
import com.haulmont.creditSystem.services.BankService;
import com.haulmont.creditSystem.services.CreditOfferService;
import com.haulmont.creditSystem.views.modalWindows.CreditOfferDeleteWindow;
import com.haulmont.creditSystem.views.modalWindows.CreditOfferEditWindow;
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
    //private Button deleteBtn = new Button("Delete", VaadinIcons.CLOSE);
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
       // deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
        refreshListBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
    }

    private void setButtonsLogic(){
        scheduleBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

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
        /*deleteBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CreditOffer selectedOffer = null;
                for(CreditOffer item : offerGrid.getSelectedItems()){
                    selectedOffer = item;
                }
                if(selectedOffer != null) {
                    openDeleteWindow(selectedOffer);
                }
                else {
                    Notification.show("Please select a credit offer");
                }

            }
        });

         */
        refreshListBtn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                findAll();
            }
        });
    }

    private void openEditWindow(CreditOffer selectedOffer){
        UI.getCurrent().addWindow(new CreditOfferEditWindow(selectedOffer));
    }

   /* private void openDeleteWindow(CreditOffer selectedOffer){
        UI.getCurrent().addWindow(new CreditOfferDeleteWindow(selectedOffer));
    }

    */

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
