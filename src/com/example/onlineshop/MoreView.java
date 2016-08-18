package com.example.onlineshop;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

public class MoreView extends ImplemntMainLayout implements View {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public MoreView() {
        // String id = (String) VaadinService.getCurrentRequest()
        // .getWrappedSession().getAttribute("id");
        int id = passProductId.getId();
        System.out.println("id" + id);
        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        root.addComponent(horizontal_bar());
        root.addComponent(More(id));
        root.addComponent(Footer());
        setCompositionRoot(root);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
