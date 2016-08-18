package com.example.onlineshop;

import com.vaadin.server.UserError;
import com.vaadin.ui.Label;

class ErrorLabel extends Label implements ErrorDisplay {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ErrorLabel() {
        setVisible(false);
    }

    @Override
    public void setError(String error) {
        setValue(error);
        setComponentError(new UserError(error));
        setVisible(true);
    }

    @Override
    public void clearError() {
        setValue(null);
        setComponentError(null);
        setVisible(false);
    }

}