package com.example.onlineshop;

import java.io.Serializable;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;

class ErrorfulFieldGroup extends FieldGroup implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ErrorDisplay errorDisplay;

    public ErrorfulFieldGroup(Item item) {
        super(item);
    }

    public void setErrorDisplay(ErrorDisplay errorDisplay) {
        this.errorDisplay = errorDisplay;
    }

    @Override
    public void commit() throws CommitException {
        try {
            super.commit();
            if (errorDisplay != null)
                errorDisplay.clearError();

        } catch (CommitException e) {
            if (errorDisplay != null)
                errorDisplay.setError(e.getCause().getMessage());

            throw e;
        }
    }

}
