package com.example.onlineshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HomeView extends ImplemntMainLayout implements View {

    private static final long serialVersionUID = 1L;

    public HomeView() {
        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        root.addComponent(horizontal_bar());
        root.addComponent(HomeContent());
        root.addComponent(Footer());
        setCompositionRoot(root);
    }

    private Component HomeContent() {

        HorizontalLayout h2 = new HorizontalLayout();
        Label errorLabel = new Label("");
        int count = 0;
        ResultSet rs1 = null;
        ResultSet rs = null;

        SimpleJDBCConnectionPool connectionPool = dbConnect();
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {

                rs1 = statement.executeQuery("SELECT COUNT(*) FROM PRODUCT");

                rs1.next();
                count = rs1.getInt(1);
                rs1.close();
                if (count == 0) {
                    errorLabel.setCaption("No items are Available");
                } else {

                    rs = statement.executeQuery("SELECT * FROM PRODUCT");

                }
            }

            catch (SQLException e) {

            }
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return IterateContent(count, rs, errorLabel, h2);

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
