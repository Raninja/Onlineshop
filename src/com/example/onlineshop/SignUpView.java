package com.example.onlineshop;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class SignUpView extends ImplemntMainLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SignUpView() {

        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        FinalSignUp sp = new FinalSignUp();
        sp.addStyleName("formstyle");
        root.addComponent(sp);
        root.addComponent(Footer());
        setCompositionRoot(root);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }
}
