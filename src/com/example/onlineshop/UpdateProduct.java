package com.example.onlineshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Label.ValueChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class UpdateProduct extends ImplemntMainLayout implements ClickListener {
    private static final long serialVersionUID = 1L;
    private final ComboBox category;
    private final ComboBox subCat1;
    TextField productName;
    TextField firstPrice;
    TextField secondPrice;
    TextArea description;
    ErrorLabel formError;
    Button updateBtn;
    Button cancelBtn;
    Navigator navigator;

    SimpleJDBCConnectionPool connectionPool = null;
    SQLContainer container = null;
    Connection conn;

    private static final String[] categories = new String[] { "Mobile Phones",
            "Computers&Laptops", "TV's", "Cameras", "Printers", "Gaming",
            "Music Systems", "Home Appliances" };

    public UpdateProduct(Item item) {
        System.out.println("Now we are in UpdateProduct.java");
        final HorizontalLayout hl = new HorizontalLayout();
        FormLayout f = new FormLayout();
        f.addComponent(new Label("Edit or Delete product details here."));

        productName = new TextField("Product Name",
                item.getItemProperty("productName"));
        productName.setRequired(true);
        productName.setRequiredError("Please enter the product name.");
        productName.setImmediate(true);
        productName.setValidationVisible(true);

        firstPrice = new TextField("First Price",
                item.getItemProperty("firstPrice"));
        firstPrice.setRequired(true);
        firstPrice.setRequiredError("Please enter the product price.");
        firstPrice.setImmediate(true);
        productName.setValidationVisible(true);
        secondPrice = new TextField("Second Price",
                item.getItemProperty("secondPrice"));

        subCat1 = new ComboBox("Select the product Sub category");

        category = new ComboBox("Select the product category");
        for (int i = 0; i < categories.length; i++) {
            category.addItem(categories[i]);
        }
        category.setImmediate(true);
        category.setRequired(true);
        category.setValidationVisible(true);
        category.setRequiredError("Category is needed");
        category.addValueChangeListener(new Property.ValueChangeListener() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unused")
            public void valueChange(ValueChangeEvent event) {
                if (category.getValue() != null) {
                    category.setValue(null);
                    category.setInputPrompt("Select another value");
                }
            }

            @Override
            public void valueChange(
                    com.vaadin.data.Property.ValueChangeEvent event) {
                String[] subCategories = {};
                // // TODO Auto-generated method stub
                if (category.getValue() == "Mobile Phones") {
                    subCategories = new String[] { "Cell Phone", "iPad",
                            "Tablets", "Mobile Accessories" };
                } else if (category.getValue() == "Computers&Laptops") {
                    subCategories = new String[] { "Laptops&Notebooks",
                            "Accessories" };
                } else if (category.getValue() == "TV's") {
                    subCategories = new String[] { "LCD", "LED", "Plasma",
                            "Color TV" };
                } else if (category.getValue() == "Cameras") {
                    subCategories = new String[] { "Digital Cameras", "DSLR" };
                } else if (category.getValue() == "Printers") {
                    subCategories = new String[] { "InkJet Printer",
                            "All In One Printer" };
                } else if (category.getValue() == "Gaming") {
                    subCategories = new String[] { "PC Games",
                            "Educational CD's" };
                }

                else if (category.getValue() == "Music Systems") {
                    subCategories = new String[] { "DVD Players", "MP3 Player",
                            "Home Theater" };
                }

                else if (category.getValue() == "Home Appliances") {
                    subCategories = new String[] { "Washing Machines",
                            "Microwaves", "Refrigerator" };
                }

                subCat1.removeAllItems();

                for (int i = 0; i < subCategories.length; i++) {
                    subCat1.addItem(subCategories[i]);
                }
            }
        });

        subCat1.setImmediate(true);
        subCat1.setRequired(true);
        subCat1.setValidationVisible(true);
        subCat1.setRequiredError("Sub Category  is needed");

        description = new TextArea("Product Description",
                item.getItemProperty("description"));
        description.setImmediate(true);
        description.setRequired(true);
        description.setValidationVisible(true);
        description.setRequiredError("Description is needed");

        updateBtn = new Button("Update");
        cancelBtn = new Button("Cancle");

        f.addComponent(productName);
        f.addComponent(firstPrice);
        f.addComponent(secondPrice);
        f.addComponent(category);
        f.addComponent(subCat1);
        f.addComponent(description);
        f.addComponent(FileUploadInterface());
        f.addComponent(updateBtn);
        f.addComponent(cancelBtn);
        f.setSpacing(true);
        setCompositionRoot(f);
        final ErrorfulFieldGroup binder = new ErrorfulFieldGroup(item);
        binder.setBuffered(true);
        binder.bindMemberFields(this);

        // Have an error display
        formError = new ErrorLabel();
        formError.setWidth(null);
        formError.addStyleName("error");
        f.addComponent(formError);
        binder.setErrorDisplay(formError);

        updateBtn.addClickListener(new ClickListener() {
            /**
              * 
              */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {
                    formError.setError("Product Exists!");
                    binder.commit();
                    connectionPool = dbConnect();
                    initDatabase();
                    container = createContainer("PRODUCT", connectionPool);
                    fillContainer(container);
                } catch (CommitException e) {

                } catch (SQLIntegrityConstraintViolationException e) {
                    // TODO Auto-generated catch block
                    formError.setError("Product Exists!");
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        cancelBtn.addClickListener(new ClickListener() {
            /**
              * 
              */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                binder.discard();

                getUI().getNavigator().navigateTo(
                        "main/more-product?restartApplication");
            }
        });

        hl.addComponent(cancelBtn);
        hl.addComponent(updateBtn);
        f.addComponent(hl);

        ResultSet rs = null;

        SimpleJDBCConnectionPool connectionPool = dbConnect();
        try {
            Connection conn = connectionPool.reserveConnection();
            Statement statement = conn.createStatement();
            try {

                rs = statement.executeQuery("SELECT * FROM PRODUCT WHERE ID='"
                        + passProductId.getId() + "'");
                while (rs.next()) {

                    productName.setValue(rs.getString(2));
                    firstPrice.setValue(rs.getString(3));
                    secondPrice.setValue(rs.getString(4));
                    description.setValue(rs.getString(5));
                    category.setValue(rs.getString(6));
                    subCat1.addItem(rs.getString(7));
                    subCat1.select(rs.getString(7));
                }
            } catch (SQLException e) {
            }
            statement.close();
            conn.commit();
            connectionPool.releaseConnection(conn);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        try {

            while (rs.next()) {
                System.out.println(" we are in  while , RS is :" + rs);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
            formError.setError("Could not create product table!");
            e.printStackTrace();
        }
    }

    protected void fillContainer(SQLContainer container2) throws SQLException {

        Statement statement = conn.createStatement();

        try {
            String Img = new String("");
            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM PRODUCT WHERE ID='"
                    + passProductId.getId() + "'");
            while (rs.next()) {
                if (passProductId.getFilepath() == null) {
                    Img = rs.getString(8);
                } else {
                    Img = passProductId.getFilepath();
                }
            }

            statement.execute("UPDATE PRODUCT SET " + "PRODUCTNAME ='"
                    + productName.getValue() + "', FIRSTPRICE='"
                    + firstPrice.getValue() + "', SECONDPRICE='"
                    + secondPrice.getValue() + "', DESCRIPTION='"
                    + description.getValue() + "',CATEGORY='"
                    + category.getValue() + "', SUBCATEGORY='"
                    + subCat1.getValue() + "' ,IMAGE ='" + Img + "' WHERE ID ="
                    + passProductId.getId());
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            container.commit();
            conn.close();
            org.hsqldb.DatabaseManager.closeDatabases(0);
            getUI().getNavigator().navigateTo(
                    "main/select-all-items?restartApplication");
            Notification.show("success");

        } catch (UnsupportedOperationException e) {
            formError.setError("Unsupported!");
            e.printStackTrace();
        } catch (SQLException e) {
            formError.setError("Not Supported!");
            e.printStackTrace();
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // TODO Auto-generated method stub

    }

}
