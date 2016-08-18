package com.example.onlineshop;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class
 * 
 * @param <SimpleViewDisplay>
 */
@Theme("onlineshoptheme")
@PreserveOnRefresh
public class OnlineshopUI extends UI {
    Navigator navigator;

    static Loginbox lg;

    private static final long serialVersionUID = 1L;

    @Override
    public void init(VaadinRequest request) {

        navigator = new Navigator(this, this);

        navigator.addView("main", new MainView());
        navigator.addView("main?restartApplication", MainView.class);
        navigator.addView("main/select-all-items?restartApplication",
                HomeView.class);

        navigator.addView("main/more-product?restartApplication",
                MoreView.class);
        navigator.addView("main/add-product?restartApplication",
                AddProductView.class);
        navigator.addView("main/login", new LoginView());
        navigator.addView("main/signup/", new SignUpView());
        navigator.addView("main/my-product?restartApplication",
                MyCartView.class);
        navigator.addView("main/update-product?restartApplication",
                UpdateProductView.class);
        navigator.navigateTo("main");

    }
}
