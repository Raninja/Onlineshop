package com.example.onlineshop;

import java.sql.ResultSet;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public interface MainLayout {

    Component header();

    Component horizontal_bar();

    Component Content();

    Component Footer();

    Component More(int id);

    Component FileUploadInterface();

    SimpleJDBCConnectionPool dbConnect();

    SQLContainer createContainer(String TableName,
            SimpleJDBCConnectionPool connectionPool);

    SimpleJDBCConnectionPool MemDbConnect();

    Component IterateContent(int count, ResultSet rs, Label errorLabel,
            HorizontalLayout hl);

}
