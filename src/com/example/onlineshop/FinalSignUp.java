package com.example.onlineshop;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.VerticalLayout;

public class FinalSignUp extends VerticalLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FinalSignUp() {

        Person bean = new Person();
        BeanItem<Person> item = new BeanItem<Person>(bean);

        SignUpForm form = new SignUpForm(item);
        addComponent(form);
    }

}
