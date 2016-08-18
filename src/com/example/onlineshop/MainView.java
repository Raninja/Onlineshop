package com.example.onlineshop;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class MainView extends ImplemntMainLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MainView() {

        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        root.addComponent(horizontal_bar());
        root.addComponent(Content());
        root.addComponent(Footer());
        setCompositionRoot(root);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

    /**
     * 
     */

}