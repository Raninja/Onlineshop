package com.example.onlineshop;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class AddProductView extends ImplemntMainLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public AddProductView() {
        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        root.addComponent(horizontal_bar());
        FinalAddProduct fp = new FinalAddProduct();
        fp.addStyleName("formstyle");
        root.addComponent(fp);
        root.addComponent(Footer());
        setCompositionRoot(root);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
