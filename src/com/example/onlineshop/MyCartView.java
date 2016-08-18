package com.example.onlineshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class MyCartView extends ImplemntMainLayout implements View,
        ClickListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private SimpleJDBCConnectionPool connectionPool2;
    private Connection conn2;
    private Statement statement;
    private int sum;

    public MyCartView() {

        VerticalLayout root = new VerticalLayout();
        root.addStyleName("root");
        root.addComponent(header());
        root.addComponent(horizontal_bar());
        root.addComponent(MyCart());
        root.addComponent(Footer());
        setCompositionRoot(root);
    }

    public Component MyCart() {
        ResultSet rs = null;

        HorizontalLayout hlm = new HorizontalLayout();

        hlm.setStyleName("hl3");
        final Table ta = new Table();

        ta.addContainerProperty("Product Name", String.class, null);
        ta.addContainerProperty("No.of Items", Integer.class, null);
        ta.addContainerProperty("Actual price", String.class, null);
        ta.addContainerProperty("Web Price", String.class, null);
        ta.addContainerProperty("You Saved", Integer.class, null);
        ta.addContainerProperty("Amount to Pay", Integer.class, null);
        ta.addContainerProperty("Remove", Button.class, null);

        connectionPool2 = MemDbConnect();
        try {
            conn2 = connectionPool2.reserveConnection();
            statement = conn2.createStatement();
            try {
                rs = statement.executeQuery("SELECT COUNT(*) FROM MyCart");
                rs.next();
                int count = rs.getInt(1);
                if (count == 0) {

                    Label l2 = new Label(
                            "No items are Available!Fill your cart");
                    hlm.addComponent(l2);

                } else {

                    hlm.addComponent(ta);
                    ta.setSelectable(true);
                    ta.setImmediate(true);
                    ta.setPageLength(0);
                    rs = statement.executeQuery("SELECT * FROM MyCart");
                    int i = 1;

                    while (rs.next()) {
                        final int amount = rs.getInt(9) * rs.getInt(5);
                        Button remove = new Button("Remove Item");
                        sum = sum + amount;
                        remove.addStyleName(Runo.BUTTON_LINK);
                        remove.setData(rs.getInt(2));
                        remove.addClickListener(new ClickListener() {

                            /**
                             * 
                             */
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void buttonClick(ClickEvent event) {
                                String id = event.getButton().getData()
                                        .toString();
                                Integer id1 = (Integer) event.getButton()
                                        .getData();
                                try {
                                    conn2 = connectionPool2.reserveConnection();
                                    statement = conn2.createStatement();

                                    try {
                                        ta.removeItem(id1);
                                        sum = sum - amount;
                                        statement
                                                .execute("DELETE FROM MyCart WHERE "
                                                        + "PRODUCTID='"
                                                        + id
                                                        + "'");
                                        ta.removeItem(id);
                                        ta.setColumnFooter("Amount to Pay",
                                                String.valueOf(sum));

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    statement.close();
                                    conn2.commit();
                                    connectionPool2.releaseConnection(conn2);
                                } catch (SQLException e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }

                            }

                        });
                        int j = rs.getInt(4) - rs.getInt(5);
                        ta.addItem(new Object[] { rs.getString(3),
                                rs.getInt(9), rs.getString(4), rs.getString(5),
                                new Integer(j), new Integer(amount), remove },
                                new Integer(rs.getInt(2)));
                        i = i + 1;
                    }

                }
            } catch (SQLException e) {
                Label l2 = new Label("Cart is Empty! Load Your Cart");
                hlm.addComponent(l2);
            }
            statement.close();
            conn2.commit();
            connectionPool2.releaseConnection(conn2);

        } catch (SQLException e) {
            // formError.setError("Could not create user table!");
            e.printStackTrace();
        }
        ta.setFooterVisible(true);
        ta.setColumnFooter("You Saved", "Total");
        ta.setColumnFooter("Amount to Pay", String.valueOf(sum));
        return hlm;

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
