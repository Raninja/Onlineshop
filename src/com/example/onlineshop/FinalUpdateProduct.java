package com.example.onlineshop;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CustomComponent;

public class FinalUpdateProduct extends CustomComponent {
    private static final long serialVersionUID = 1L;

    public FinalUpdateProduct() {

        Product bean = new Product();
        BeanItem<Product> item = new BeanItem<Product>(bean);

        UpdateProduct form = new UpdateProduct(item);
        setCompositionRoot(form);
    }

}
