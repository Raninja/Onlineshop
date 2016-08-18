package com.example.onlineshop;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends ImplemntMainLayout implements View {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String NAME = "count";

    public LoginView() {

        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addStyleName("root");
        root.addComponent(header());
        Loginbox lg = new Loginbox();
        lg.addStyleName("formstyle");

        root.addComponent(lg);
        root.addComponent(Footer());
        setCompositionRoot(root);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}