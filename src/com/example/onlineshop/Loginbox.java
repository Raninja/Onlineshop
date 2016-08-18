package com.example.onlineshop;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Runo;

public class Loginbox extends ImplemntMainLayout implements ClickListener,
        Serializable {

    private static final long serialVersionUID = 1L;
    public static String NAME = "guest";
    public final FormLayout form;
    private final TextField username;

    private final PasswordField password;
    private final Button login;
    private final Button cancel;
    private final HorizontalLayout hl;
    private final Button signup;
    private SimpleJDBCConnectionPool connectionPool = null;
    private final Label errorlabel;
    private Connection conn;
    String v = null;

    /*
     * public String getV() { return v; }
     */

    SQLContainer container = null;

    @SuppressWarnings("deprecation")
    public Loginbox() {

        hl = new HorizontalLayout();
        form = new FormLayout();
        form.addComponent(new Label("Login"));
        username = new TextField("UserName");
        password = new PasswordField("Password");
        login = new Button("LogIn", this);

        errorlabel = new Label();
        errorlabel.addStyleName("fo");
        login.addClickListener(new ClickListener() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                String user = username.getValue().toString();
                String pass = password.getValue().toString();
                if (user.equals("admin") && pass.equals("admin")) {

                    VaadinService.getCurrentRequest().getWrappedSession()
                            .setAttribute("myvalue", user);
                    username.setValue("");
                    password.setValue("");
                    getUI().getNavigator()
                            .navigateTo("main?restartApplication");
                } else if (user.equals("") || pass.equals("")) {
                    errorlabel.setCaption("Login Failed!Enter All fields");

                } else {
                    connectionPool = dbConnect();
                    try {
                        initDatabase(user, pass);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        });

        login.setClickShortcut(KeyCode.ENTER);

        cancel = new Button("Cancel");
        signup = new Button("Create New Account");
        signup.addStyleName(Runo.BUTTON_LINK);
        signup.setWidth(15, UNITS_EM);
        signup.addStyleName("signup");
        signup.addClickListener(this);
        username.setRequired(true);
        password.setRequired(true);

        cancel.addClickListener(this);
        form.addComponent(username);
        username.focus();
        form.addComponent(password);
        hl.addComponent(signup);
        hl.addComponent(login);
        hl.addComponent(cancel);
        form.addComponent(hl);
        form.addComponent(signup);
        form.addComponent(errorlabel);
        setCompositionRoot(form);

    }

    public void initDatabase(String user, String pass) throws SQLException {

        conn = connectionPool.reserveConnection();
        Statement statement = conn.createStatement();
        System.out.println("init...");
        try {

            ResultSet rs = statement.executeQuery("SELECT COUNT(*) from"
                    + " USERS WHERE USERNAME='" + user + "' AND PASSWORD='"
                    + pass + "'");
            rs.next();
            if (rs.getInt(1) != 1) {
                errorlabel
                        .setCaption("Login Failed! Username and password are not matching");
            } else {

                VaadinService.getCurrentRequest().getWrappedSession()
                        .setAttribute("myvalue", user);

                username.setValue("");
                username.setValue("");
                getUI().getNavigator().navigateTo("main?restartApplication");
            }

        } catch (SQLException e) {
            errorlabel
                    .setCaption("Could not create an instance of SQLContainer!");
            e.printStackTrace();
        }
        statement.close();
        conn.commit();
        connectionPool.releaseConnection(conn);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == cancel) {
            username.setValue("");
            password.setValue("");
        } else if (event.getButton() == signup) {
            getUI().getNavigator().navigateTo("main/signup/");
        }
    }

}
