package com.example.onlineshop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import elemental.events.KeyboardEvent.KeyCode;

public class SignUpForm extends ImplemntMainLayout implements ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    TextField firstName;
    TextField lastName;

    TextField emailId;
    TextField phoneNo;
    TextArea address;
    TextField userName;
    PasswordField password;
    PasswordField confirmPassword;
    ErrorLabel formError;
    SimpleJDBCConnectionPool connectionPool = null;
    SQLContainer container = null;
    Connection conn;

    public SignUpForm(Item item) {

        final HorizontalLayout hl = new HorizontalLayout();
        final VerticalLayout content = new VerticalLayout();
        firstName = new TextField("First Name",
                item.getItemProperty("firstName"));
        firstName.focus();
        firstName.setRequired(true);
        firstName.setRequiredError("First Name Must be given");
        firstName.addValidator(new BeanValidator(Person.class, "firstName"));
        firstName.setImmediate(true);
        firstName.setValidationVisible(true);

        lastName = new TextField("Last Name", item.getItemProperty("lastName"));
        lastName.setRequired(true);
        lastName.setRequiredError("Last Name Must be given");
        lastName.addValidator(new BeanValidator(Person.class, "lastName"));
        lastName.setImmediate(true);
        lastName.setValidationVisible(true);

        userName = new TextField("User Name", item.getItemProperty("userName"));
        userName.setRequired(true);
        userName.setRequiredError("Username Must be given");
        userName.addValidator(new BeanValidator(Person.class, "userName"));
        userName.setImmediate(true);

        userName.setValidationVisible(true);

        password = new PasswordField("Password",
                item.getItemProperty("password"));
        password.setRequired(true);
        password.setRequiredError("Password Must be given");
        password.addValidator(new BeanValidator(Person.class, "password"));
        password.setImmediate(true);
        password.setValidationVisible(true);

        confirmPassword = new PasswordField("Confirm Password",
                item.getItemProperty("confirmPassword"));
        confirmPassword.setRequired(true);
        confirmPassword.setRequiredError("Password Must be given");
        confirmPassword.addValidator(new BeanValidator(Person.class,
                "confirmPassword"));

        confirmPassword.setImmediate(true);
        confirmPassword.setValidationVisible(true);

        emailId = new TextField("Email-Id", item.getItemProperty("emailId"));
        emailId.setRequired(true);
        emailId.setRequiredError("Email Must be given");
        emailId.addValidator(new BeanValidator(Person.class, "emailId"));
        emailId.setImmediate(true);
        emailId.setValidationVisible(true);

        phoneNo = new TextField("Phone Number", item.getItemProperty("phoneNo"));
        phoneNo.setRequired(true);
        phoneNo.setRequiredError("Phone Number Must be given");
        phoneNo.addValidator(new BeanValidator(Person.class, "phoneNo"));
        phoneNo.setImmediate(true);
        phoneNo.setValidationVisible(true);

        address = new TextArea("Address", item.getItemProperty("address"));
        address.setRequired(true);
        address.setRequiredError("Address Must be given");
        address.addValidator(new BeanValidator(Person.class, "address"));
        address.setImmediate(true);
        address.setValidationVisible(true);

        // Build the form
        FormLayout form = new FormLayout();

        form.addComponent(firstName);
        form.addComponent(lastName);
        form.addComponent(emailId);
        form.addComponent(phoneNo);
        form.addComponent(address);
        form.addComponent(userName);
        form.addComponent(password);
        form.addComponent(confirmPassword);

        content.addComponent(form);

        // Bind the form
        final ErrorfulFieldGroup binder = new ErrorfulFieldGroup(item);
        binder.setBuffered(true);
        binder.bindMemberFields(this);

        // Have an error display
        formError = new ErrorLabel();
        formError.setWidth(null);
        formError.addStyleName("error");
        content.addComponent(formError);
        binder.setErrorDisplay(formError);

        // Commit button
        Button ok = new Button("OK");
        ok.addClickListener(new ClickListener() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                String v = password.getValue();
                String v2 = confirmPassword.getValue();
                if (!v.equals(v2) && v != "" && v2 != "") {
                    confirmPassword.setComponentError(new UserError(
                            "Passwords are not Matching"));
                    formError.setError("Passwords are not Matching");

                } else if (v != "" && v2 == "") {
                    confirmPassword.setComponentError(new UserError(
                            "Password Confirmation is needed"));
                    formError.setError("Password Confirmation is needed");
                } else {

                    try {
                        confirmPassword.setComponentError(null);
                        binder.commit();
                        connectionPool = dbConnect();
                        initDatabase();
                        container = createContainer("USERS", connectionPool);
                        fillContainer(container);
                        Notification.show("Success! Now you have registered");
                        VaadinService.getCurrentRequest().getWrappedSession()
                                .setAttribute("myvalue", userName.getValue());
                        userName.setValue("");
                        firstName.setValue("");
                        lastName.setValue("");
                        emailId.setValue("");
                        phoneNo.setValue("");
                        password.setValue("");
                        confirmPassword.setValue("");
                        address.setValue("");
                        getUI().getNavigator().navigateTo(
                                "main?restartApplication");

                    } catch (CommitException e) {

                    } catch (SQLIntegrityConstraintViolationException e) {
                        // TODO Auto-generated catch block
                        formError.setError("Username Exists!");
                    }

                }

            }

        });
        ok.setClickShortcut(KeyCode.ENTER);
        Button cancel = new Button("Cancel");
        cancel.addClickListener(new ClickListener() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                binder.discard();
            }
        });
        hl.addComponent(ok);
        hl.addComponent(cancel);

        content.addComponent(hl);

        setCompositionRoot(content);

    }

    protected void initDatabase() {
        try {
            conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            System.out.println("init...");
            try {

                statement.executeQuery("SELECT * FROM USERS");
            } catch (SQLException e) {

                statement.execute("CREATE TABLE USERS "
                        + "(ID INTEGER GENERATED ALWAYS AS IDENTITY, "
                        + "USERNAME VARCHAR(32) UNIQUE, PASSWORD VARCHAR(32), "
                        + "FIRSTNAME VARCHAR(32), LASTNAME VARCHAR(32), "
                        + "EMAIL VARCHAR(32), PHONENO VARCHAR(32), "
                        + "ADDRESS VARCHAR(32), " + "PRIMARY KEY(ID))");
            }
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);
        } catch (SQLException e) {
            formError.setError("Could not create user table!");
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    protected void fillContainer(SQLContainer container2)
            throws SQLIntegrityConstraintViolationException {
        Object id = container.addItem();
        container.getContainerProperty(id, "USERNAME").setValue(
                userName.getValue());
        container.getContainerProperty(id, "PASSWORD").setValue(
                password.getValue());
        container.getContainerProperty(id, "FIRSTNAME").setValue(
                firstName.getValue());
        container.getContainerProperty(id, "LASTNAME").setValue(
                lastName.getValue());
        container.getContainerProperty(id, "EMAIL")
                .setValue(emailId.getValue());
        container.getContainerProperty(id, "PHONENO").setValue(
                phoneNo.getValue());
        container.getContainerProperty(id, "ADDRESS").setValue(
                address.getValue());
        try {
            container.commit();

            conn.close();
            org.hsqldb.DatabaseManager.closeDatabases(0);

        } catch (UnsupportedOperationException e) {
            formError.setError("Username Exists!");
            e.printStackTrace();
        } catch (SQLException e) {
            formError.setError("Username Exists!");
            e.printStackTrace();
        }

    }

    public SignUpForm() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // TODO Auto-generated method stub

    }

}