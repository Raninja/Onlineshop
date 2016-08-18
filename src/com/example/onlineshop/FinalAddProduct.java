package com.example.onlineshop;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.VerticalLayout;

public class FinalAddProduct extends VerticalLayout {
    private static final long serialVersionUID = 1L;

    public FinalAddProduct() {

        Product bean = new Product();
        BeanItem<Product> item = new BeanItem<Product>(bean);

        AddProduct form = new AddProduct(item);
        addComponent(form);

    }

}
