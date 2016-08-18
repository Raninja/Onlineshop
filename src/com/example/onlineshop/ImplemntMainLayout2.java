package com.example.onlineshop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

public class ImplemntMainLayout2 extends CustomComponent implements MainLayout,
        ClickListener {

    SimpleJDBCConnectionPool connectionPool = null;
    SQLContainer container = null;
    Connection conn;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public Component header() {

        HorizontalLayout root_link = new HorizontalLayout();
        HorizontalLayout root_hl = new HorizontalLayout();
        TextField search = new TextField();
        Label search_name = new Label("Search");
        search_name.setWidth("100%");
        Button go = new Button("Go");
        go.addStyleName("go");
        go.addStyleName(Runo.BUTTON_SMALL);
        root_hl.addComponent(search_name);
        root_hl.addComponent(search);
        root_hl.addComponent(go);
        root_hl.addStyleName("roothl");

        Button logout = new Button("Logout", new ClickListener() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                VaadinService.getCurrentRequest().getWrappedSession()
                        .setAttribute("myvalue", null);
                getUI().getNavigator().navigateTo("main?restartApplication");
            }
        });
        logout.addStyleName(Runo.BUTTON_LINK);
        Button login = new Button("Login", new ButtonListener("login"));
        login.addStyleName(Runo.BUTTON_LINK);
        Button newuser = new Button("New User", new ButtonListener("signup"));

        newuser.addStyleName(Runo.BUTTON_LINK);

        Button mycart = new Button("My Cart", new ButtonListener("MyCart"));
        mycart.addStyleName(Runo.BUTTON_LINK);
        Button home = new Button("Home", new ButtonListener("Home"));
        home.addStyleName(Runo.BUTTON_LINK);
        root_link.addComponent(home);

        String name = (String) VaadinService.getCurrentRequest()
                .getWrappedSession().getAttribute("myvalue");
        root_link.addComponent(login);

        if (name != null) {
            root_link.replaceComponent(login, logout);

        }
        root_link.addComponent(newuser);
        root_link.addComponent(mycart);
        root_link.setSpacing(true);
        root_link.addStyleName("rootlink");

        ThemeResource resource = new ThemeResource("img/main.png");

        Embedded image1 = new Embedded("", resource);
        // Notification.show(image1.toString());
        GridLayout gridl1 = new GridLayout(2, 3);
        gridl1.setSizeUndefined();
        gridl1.addStyleName("gridl1");
        gridl1.addComponent(image1, 0, 0, 0, 1);
        gridl1.addComponent(root_link, 1, 0);
        gridl1.addComponent(root_hl, 1, 1);

        MenuBar main_menu = new MenuBar();
        main_menu.setImmediate(true);
        MenuBar.Command mycommand = new MenuBar.Command() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void menuSelected(MenuItem selectedItem) {
                passProductId.setMenuselect(selectedItem.getText());
                getUI().getNavigator().navigateTo("main?restartApplication");

            }

        };
        main_menu.addStyleName("menustyle");
        gridl1.addComponent(main_menu, 0, 2, 1, 2);
        main_menu.setSizeUndefined();

        MenuBar.MenuItem mobile = main_menu.addItem("Mobile Phones", null);
        mobile.addItem("Cell Phone", mycommand);
        mobile.addItem("iPad", mycommand);
        mobile.addItem("Tablets", mycommand);
        mobile.addItem("Mobile Accessories", mycommand);

        MenuBar.MenuItem compu_lap = main_menu.addItem("Computers&Laptops",
                null);
        compu_lap.addItem("Laptops&Notebooks", mycommand);
        compu_lap.addItem("Computer Accessories", mycommand);

        MenuBar.MenuItem tv = main_menu.addItem("TV's", null);
        tv.addItem("LCD", mycommand);
        tv.addItem("LED", mycommand);
        tv.addItem("Plasma", mycommand);
        tv.addItem("Color Tv", mycommand);

        MenuBar.MenuItem cameras = main_menu.addItem("Cameras", null);
        cameras.addItem("Digital Cameras", mycommand);
        cameras.addItem("DSLR", mycommand);

        MenuBar.MenuItem printers = main_menu.addItem("Printers", null);
        printers.addItem("InkJet Printer", mycommand);
        printers.addItem("All In One Printer", mycommand);
        MenuBar.MenuItem games = main_menu.addItem("Gaming", null);
        games.addItem("PC Games", mycommand);
        games.addItem("Educational CD's", mycommand);

        MenuBar.MenuItem music_syst = main_menu.addItem("Music Systems", null);
        music_syst.addItem("DVD Players", mycommand);
        music_syst.addItem("MP3 Player", mycommand);
        music_syst.addItem("Home Theater", mycommand);

        MenuBar.MenuItem home_app = main_menu.addItem("Home Appliances", null);
        home_app.addItem("Washing Machines", mycommand);
        home_app.addItem("Microwaves", mycommand);
        home_app.addItem("Refrigerator", mycommand);

        return gridl1;

    }

    @Override
    public Component horizontal_bar() {
        VerticalLayout v_hr = new VerticalLayout();

        ThemeResource resource1 = new ThemeResource("img/mobile_layout.png");
        Embedded image3 = new Embedded("", resource1);
        image3.addStyleName("resource");
        v_hr.addComponent(image3);

        Label hr = new Label(
                "<hr style='width:57%;margin-left:16pc;height:7px;'/>",
                ContentMode.HTML);
        String name = (String) VaadinService.getCurrentRequest()
                .getWrappedSession().getAttribute("myvalue");
        if (name == null) {
            name = "guest!";
        }
        Label welcome = new Label("Welcome" + " " + name);
        welcome.setStyleName("welcomestyle");
        v_hr.addComponent(welcome);

        v_hr.addComponent(hr);

        return v_hr;
    }

    @Override
    public Component Content() {
        HorizontalLayout h2 = new HorizontalLayout();
        Label errorLabel = new Label("");
        int count = 0;
        ResultSet rs = null;
        String menuitem = passProductId.getMenuselect();
        if (menuitem == null) {
            menuitem = "Cell Phone";
        }
        SimpleJDBCConnectionPool connectionPool = dbConnect();
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {

                rs = statement
                        .executeQuery("SELECT COUNT(*) FROM PRODUCT WHERE SUBCATEGORY='"
                                + menuitem + "'");
                rs.next();
                count = rs.getInt(1);
                if (count == 0) {
                    errorLabel.setCaption("No items are Available");
                } else {
                    rs = statement
                            .executeQuery("SELECT * FROM PRODUCT WHERE SUBCATEGORY='"
                                    + menuitem + "'");
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

        if (count > 0) {
            int p = 0;
            if (count % 3 == 0) {
                p = count / 3;
            } else {
                p = (count / 3) + 1;
            }
            GridLayout gd = new GridLayout(3, p);
            try {
                while (rs.next()) {
                    VerticalLayout vt = new VerticalLayout();
                    ThemeResource resource = new ThemeResource(rs.getString(8));
                    Embedded image2 = new Embedded("", resource);
                    image2.setWidth("100px");
                    image2.setHeight("100px");
                    vt.addComponent(image2);
                    final int id = rs.getInt(1);
                    GridLayout gt = new GridLayout(2, 5);

                    Label Title = new Label(rs.getString(2));
                    Title.setStyleName("price1");
                    int i = rs.getInt(3) - rs.getInt(4);
                    Label actual = new Label("Actual Price:" + rs.getString(3));
                    if (rs.getString(4) != null) {
                        actual.setStyleName("price2");
                    }
                    System.out.println("webprice" + rs.getInt(4));

                    Label message = new Label(
                            "<span style='color:red;font-weight:bold;'>You Saved....</span>"
                                    + i, ContentMode.HTML);

                    Label web = new Label("Web Price:" + rs.getString(4));
                    Button more = new Button("More...", new ClickListener() {

                        /**
                     * 
                     */
                        private static final long serialVersionUID = 1L;

                        @Override
                        public void buttonClick(ClickEvent event) {
                            // VaadinService.getCurrentRequest()
                            // .getWrappedSession().setAttribute("id",rs.getInt(1));

                            passProductId.setId(id);
                            getUI().getNavigator().navigateTo(
                                    "main/more-product?restartApplication");
                        }
                    });
                    more.addStyleName("more");
                    more.addStyleName(Runo.BUTTON_LINK);

                    gt.addComponent(Title, 0, 0);
                    gt.addComponent(actual, 0, 1);
                    gt.addComponent(web, 0, 2);
                    gt.addComponent(message, 0, 3);
                    gt.addComponent(more, 1, 4);
                    gt.addStyleName("price");
                    vt.addComponent(gt);
                    vt.addStyleName("image");
                    vt.setSizeUndefined();
                    gd.addComponent(vt);
                }
            } catch (OverlapsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OutOfBoundsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            gd.setSpacing(true);
            gd.addStyleName("gd");

            return gd;
        } else {

            h2.addComponent(errorLabel);
            h2.setStyleName("hl3");
            passProductId.setMenuselect("Cell Phone");
            return h2;
        }
    }

    @Override
    public Component Footer() {
        VerticalLayout v1 = new VerticalLayout();
        v1.addStyleName("footalign");
        VerticalLayout v2 = new VerticalLayout();
        VerticalLayout v3 = new VerticalLayout();
        v3.addStyleName("footalign");
        v2.addStyleName("footalign");

        Label shop = new Label("<b>Shop With Confidence</b>", ContentMode.HTML);
        shop.addStyleName("shop");
        Label custom = new Label("<b>Customer Support</b>", ContentMode.HTML);
        custom.addStyleName("shop");
        Label shop_guide = new Label("<b>How It Works</b>", ContentMode.HTML);
        shop.addStyleName("shop");

        Button how_works = new Button("How It Works");
        how_works.addStyleName("white");
        how_works.addStyleName(Runo.BUTTON_LINK);
        Button faq = new Button("FAQs");
        faq.addStyleName("white");
        faq.addStyleName(Runo.BUTTON_LINK);
        Button aboutus = new Button("About Us");
        aboutus.addStyleName(Runo.BUTTON_LINK);
        aboutus.addStyleName("white");
        Button contactus = new Button("Contact Us");
        contactus.addStyleName(Runo.BUTTON_LINK);
        contactus.addStyleName("white");

        v1.addComponent(shop);
        v1.addComponent(faq);
        v1.setSpacing(true);
        v2.addComponent(custom);
        v2.addComponent(aboutus);
        v2.addComponent(contactus);
        v2.setSpacing(true);
        v3.addComponent(shop_guide);
        v3.setSpacing(true);
        v3.addComponent(how_works);

        HorizontalLayout footer = new HorizontalLayout();
        footer.addComponent(v1);
        footer.addComponent(v2);
        footer.addComponent(v3);

        footer.addStyleName("footer");
        footer.setSpacing(true);

        return footer;
    }

    class ButtonListener implements Button.ClickListener {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        String buttonitem;

        public ButtonListener(String buttonitem) {
            this.buttonitem = buttonitem;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            if (buttonitem == "login") {
                getUI().getNavigator().navigateTo("main/login/");

            } else if (buttonitem == "signup") {
                getUI().getNavigator().navigateTo("main/signup/");
            } else if (buttonitem == "Home") {
                getUI().getNavigator().navigateTo("main?restartApplication");
            } else if (buttonitem == "AddProduct") {
                getUI().getNavigator().navigateTo(
                        "main/add-product?restartApplication");
            } else if (buttonitem == "MyCart") {
                getUI().getNavigator().navigateTo(
                        "main/my-product?restartApplication");
            } else if (buttonitem == "editProduct") {
                getUI().getNavigator().navigateTo(
                        "main/update-product?restartApplication");
            }
        }

    }

    @Override
    public void buttonClick(ClickEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public Component More(int id) {
        ResultSet rs = null;
        Embedded image2 = null;
        Label tit = new Label("");
        Label secondPrice = new Label("");
        Label desc = new Label("");
        Label firstPrice = new Label("");
        GridLayout moregrid = new GridLayout(2, 2);
        final Label ProductId = new Label("");
        final Label ProductName = new Label("");
        final Label FirstPrice = new Label("");
        final Label SecondPrice = new Label("");
        final Label Category = new Label("");
        final Label SubCategory = new Label("");
        final Label description = new Label("");
        final ComboBox nopieces = new ComboBox("Select No.of Pieces");
        for (int j = 1; j < 11; j++) {
            nopieces.addItem(j);
        }
        nopieces.select(1);
        final SimpleJDBCConnectionPool connectionPool = dbConnect();
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {
                rs = statement.executeQuery("SELECT * FROM PRODUCT WHERE ID='"
                        + id + "'");
            }

            catch (SQLException e) {

            }
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        try {
            while (rs.next()) {
                ThemeResource resource = new ThemeResource(rs.getString(8));
                image2 = new Embedded("", resource);
                image2.setWidth("200px");
                image2.setHeight("200px");
                image2.addStyleName("image");
                tit.setCaption(rs.getString(2));
                ProductId.setValue(rs.getString(1));
                ProductName.setValue(rs.getString(2));
                FirstPrice.setValue(rs.getString(3));
                SecondPrice.setValue(rs.getString(4));
                Category.setValue(rs.getString(6));
                SubCategory.setValue(rs.getString(7));
                description.setValue(rs.getString(5));
                firstPrice.setCaption("Actual price:" + "" + rs.getString(3));
                secondPrice.setCaption("Online price:" + "" + rs.getString(4));
                desc.setCaption(rs.getString(5));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Button AddCart = new Button("Add To Cart", new ClickListener() {

            /**
                 * 
                 */
            private static final long serialVersionUID = 1L;
            private Connection conn;
            private SimpleJDBCConnectionPool connectionPool;
            private SQLContainer container;

            @Override
            public void buttonClick(ClickEvent event) {
                connectionPool = MemDbConnect();
                initDatabase();
                container = createContainer("Mycart", connectionPool);
                try {
                    fillContainer(container);
                } catch (SQLIntegrityConstraintViolationException e) {
                    // TODO Auto-generated catch block
                    Notification.show("You have already added the item");
                }

            }

            @SuppressWarnings("unchecked")
            protected void fillContainer(SQLContainer container2)
                    throws SQLIntegrityConstraintViolationException {
                Object id = container.addItem();

                container.getContainerProperty(id, "FIRSTPRICE").setValue(
                        FirstPrice.getValue());

                container.getContainerProperty(id, "PRODUCTNAME").setValue(
                        ProductName.getValue());

                container.getContainerProperty(id, "SECONDPRICE").setValue(
                        SecondPrice.getValue());

                container.getContainerProperty(id, "DESCRIPTION").setValue(
                        description.getValue());

                container.getContainerProperty(id, "CATEGORY").setValue(
                        Category.getValue());

                container.getContainerProperty(id, "SUBCATEGORY").setValue(
                        SubCategory.getValue());

                container.getContainerProperty(id, "PRODUCTID").setValue(
                        ProductId.getValue());
                container.getContainerProperty(id, "NOPIECES").setValue(
                        nopieces.getValue().toString());

                try {
                    container.commit();
                    System.out.print("container is commited now !");
                    conn.close();
                    org.hsqldb.DatabaseManager.closeDatabases(0);
                    Notification.show("success");
                    getUI().getNavigator()
                            .navigateTo("main?restartApplication");

                } catch (UnsupportedOperationException e) {

                    e.printStackTrace();
                } catch (SQLException e) {

                    Notification.show("You have already added the item");
                    e.printStackTrace();
                }
            }

            private void initDatabase() {
                try {
                    conn = connectionPool.reserveConnection();
                    Statement statement = conn.createStatement();
                    System.out.println("init...");
                    try {

                        ResultSet rs = statement
                                .executeQuery("SELECT * FROM Mycart");
                        while (rs.next()) {

                        }

                    } catch (SQLException e) {

                        statement.execute("CREATE TABLE Mycart "
                                + "(ID INTEGER GENERATED ALWAYS AS IDENTITY, "
                                + "PRODUCTID VARCHAR(10) UNIQUE,"
                                + "PRODUCTNAME VARCHAR(32), "
                                + "FIRSTPRICE VARCHAR(32),"
                                + "SECONDPRICE VARCHAR(32),"
                                + "DESCRIPTION LONGVARCHAR,"
                                + "CATEGORY VARCHAR(32),"
                                + "SUBCATEGORY VARCHAR(32),"
                                + "NOPIECES VARCHAR(32)," + "PRIMARY KEY(ID))");

                    }
                    statement.close();
                    conn.commit();
                    connectionPool.releaseConnection(conn);

                } catch (SQLException e) {
                    // formError.setError("Could not create user table!");
                    e.printStackTrace();
                }

            }
        });

        HorizontalLayout hl_button = new HorizontalLayout();
        HorizontalLayout hpieces = new HorizontalLayout();
        VerticalLayout vl = new VerticalLayout();
        Button BuyNow = new Button("Buy Now", new ButtonListener("buyNow"));
        Button editProduct = new Button("Edit", new ButtonListener(
                "editProduct"));
        Button deleteProduct = new Button("Delete");
        deleteProduct.addClickListener(new ClickListener() {
            /**
     * 
     */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    conn = connectionPool.reserveConnection();
                    Statement statement = conn.createStatement();
                    try {

                        statement.execute("DELETE FROM PRODUCT WHERE ID ="
                                + passProductId.getId());
                        Notification.show("DELETED");
                        getUI().getNavigator().navigateTo("main");

                    }

                    catch (SQLException e) {

                        System.out.println("errrror");
                    }
                    statement.close();
                    conn.commit();
                    connectionPool.releaseConnection(conn);

                } catch (SQLException e) {
                    Notification.show("Could not create product table!");
                    e.printStackTrace();
                }
            }

            // TODO Auto-generated method stub

        });
        BuyNow.addStyleName("go");
        AddCart.addStyleName("go");
        editProduct.addStyleName("go");
        deleteProduct.addStyleName("go");

        String name = (String) VaadinService.getCurrentRequest()
                .getWrappedSession().getAttribute("myvalue");
        System.out.println("The user is : ++++++" + name);
        if (name == null) {
            hl_button.addComponent(BuyNow);
            hl_button.addComponent(AddCart);
        } else if (name.equals("admin")) {

            hl_button.addComponent(editProduct);
            hl_button.addComponent(deleteProduct);
        }

        hl_button.addStyleName("hbutton");
        tit.setStyleName("price1");
        vl.addComponent(tit);
        vl.addComponent(firstPrice);
        vl.addComponent(secondPrice);
        vl.addComponent(desc);
        hpieces.addComponent(nopieces);
        vl.addComponent(hpieces);
        vl.setStyleName("vbutton");
        moregrid.addComponent(image2, 0, 0, 0, 1);
        moregrid.addComponent(vl, 1, 0);
        moregrid.addComponent(hl_button, 1, 1);
        moregrid.addStyleName("gd");

        moregrid.setSpacing(true);
        return moregrid;
    }

    @Override
    public Component FileUploadInterface() {

        final Embedded image = new Embedded("Uploaded Image");
        Upload upload1 = new Upload("upload Image here", null);
        VerticalLayout fileform = new VerticalLayout();
        upload1.setButtonCaption("Start Upload");
        fileform.addComponent(upload1);
        image.setVisible(false);
        fileform.addComponent(image);

        class ImageUploader implements Receiver, SucceededListener {

            private static final long serialVersionUID = 1L;

            public File file;

            @SuppressWarnings("deprecation")
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {

                FileOutputStream fos = null;
                try {
                    file = new File(
                            "C:/Users/Shohreh/workspace/OnlineShop/WebContent/VAADIN/themes/onlineshoptheme/img/"
                                    + filename);
                    System.out.println("filename" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    Notification.show("Could not open file<br/>",
                            e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                    return null;
                }

                String filepath = "img/" + file.getName();
                passProductId.setFilepath(filepath);
                System.out.println(passProductId.getFilepath());
                return fos;
            }

            @Override
            public void uploadSucceeded(SucceededEvent event) {

                image.setVisible(true);
                image.setSource(new FileResource(file));
            }

        }
        ;

        final ImageUploader uploader = new ImageUploader();
        upload1.setReceiver(uploader);
        upload1.addSucceededListener(uploader);

        return fileform;
    }

    @Override
    public SimpleJDBCConnectionPool dbConnect() {
        SimpleJDBCConnectionPool connectionPool = null;
        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "org.hsqldb.jdbc.JDBCDriver",
                    "jdbc:hsqldb:file:C://Users//Shohreh//workspace//OnlineShop//mydb",
                    "SA", "", 2, 5);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionPool;
    }

    @Override
    public SQLContainer createContainer(String TableName,
            SimpleJDBCConnectionPool connectionPool) {
        SQLContainer container = null;
        try {

            TableQuery query = new TableQuery(TableName, connectionPool);
            query.setVersionColumn("OPTLOCK");
            container = new SQLContainer(query);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return container;
    }

    protected void initDatabase() {
        try {
            conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {

                statement.executeQuery("SELECT * FROM PRODUCT");

            }

            catch (SQLException e) {

                statement.execute("CREATE TABLE PRODUCT "
                        + "(ID INTEGER GENERATED ALWAYS AS IDENTITY, "
                        + "PRODUCTNAME VARCHAR(32) UNIQUE, "
                        + "FIRSTPRICE VARCHAR(32),"
                        + "SECONDPRICE VARCHAR(32),"
                        + "DESCRIPTION LONGVARCHAR," + "CATEGORY VARCHAR(32),"
                        + "SUBCATEGORY VARCHAR(32)," + " IMAGE LONGVARCHAR, "
                        + "PRIMARY KEY(ID))");

            }
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);

        } catch (SQLException e) {
            Notification.show("Could not create product table!");
            e.printStackTrace();
        }
    }

    @Override
    public SimpleJDBCConnectionPool MemDbConnect() {

        SimpleJDBCConnectionPool connectionPool = null;
        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "org.hsqldb.jdbc.JDBCDriver", "jdbc:hsqldb:mem:mycart",
                    "SA", "", 2, 5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connectionPool;
    }

    protected void fillContainer(SQLContainer container2) throws SQLException {
        Statement statement = conn.createStatement();
        try {
            System.out.println("Go0o0o0o0o0o0d Night");
            statement.execute("DELETE FROM PRODUCT WHERE ID ="
                    + passProductId.getId());
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            container.commit();
            System.out.println("container is commited now !");
            conn.close();
            org.hsqldb.DatabaseManager.closeDatabases(0);
            Notification.show("success");

        } catch (UnsupportedOperationException e) {
            Notification.show("Unsupported!");
            e.printStackTrace();
        } catch (SQLException e) {
            Notification.show("Unsupported!");
            e.printStackTrace();
        }
        statement.close();
        conn.commit();
        connectionPool.releaseConnection(conn);
    }

    @Override
    public Component IterateContent(int count, ResultSet rs, Label errorLabel,
            HorizontalLayout hl) {
        // TODO Auto-generated method stub
        return null;
    }

}
