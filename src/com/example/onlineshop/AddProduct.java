package com.example.onlineshop;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
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

public class AddProduct extends ImplemntMainLayout implements ClickListener {
    private static final long serialVersionUID = 1L;

    TextField productName;
    TextField firstPrice;
    TextField secondPrice;
    TextArea description;
    String category1;

    private final ComboBox category;
    private final ComboBox subCat1;

    ErrorLabel formError;
    SimpleJDBCConnectionPool connectionPool = null;
    SQLContainer container = null;
    Connection conn;

    Button btnsave;
    Button cancel;

    private static final String[] categories = new String[] { "Mobile Phones",
            "Computers&Laptops", "TV's", "Cameras", "Printers", "Gaming",
            "Music Systems", "Home Appliances" };

    public AddProduct(final Item item) {

        final HorizontalLayout hl = new HorizontalLayout();

        FormLayout f = new FormLayout();
        f.addComponent(new Label("Add new Product"));

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

        Button savebtn = new Button("Save");
        Button cancel = new Button("Cancel");

        f.addComponent(productName);
        f.addComponent(firstPrice);
        f.addComponent(secondPrice);
        f.addComponent(category);
        f.addComponent(subCat1);
        f.addComponent(description);
        f.addComponent(FileUploadInterface());
        f.addComponent(savebtn);
        f.addComponent(cancel);
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

        savebtn.addClickListener(new ClickListener() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                try {

                    formError.setError("Username Exists!");

                    binder.commit();

                    connectionPool = dbConnect();
                    initDatabase();
                    container = createContainer("PRODUCT", connectionPool);

                    fillContainer(container);

                } catch (CommitException e) {

                } catch (SQLIntegrityConstraintViolationException e) {
                    // TODO Auto-generated catch block
                    formError.setError("Username Exists!");
                    System.out.println("Usrname Existss");
                }

            }

        });

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

        hl.addComponent(cancel);
        hl.addComponent(savebtn);
        f.addComponent(hl);

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

    @SuppressWarnings("unchecked")
    protected void fillContainer(SQLContainer container2)
            throws SQLIntegrityConstraintViolationException {
        Object id = container.addItem();

        container.getContainerProperty(id, "PRODUCTNAME").setValue(
                productName.getValue());
        container.getContainerProperty(id, "FIRSTPRICE").setValue(
                firstPrice.getValue());
        container.getContainerProperty(id, "SECONDPRICE").setValue(
                secondPrice.getValue());
        container.getContainerProperty(id, "DESCRIPTION").setValue(
                description.getValue());

        container.getContainerProperty(id, "CATEGORY").setValue(
                category.getValue());

        container.getContainerProperty(id, "SUBCATEGORY").setValue(
                subCat1.getValue());

        System.out.println("we gave" + passProductId.getFilepath().length());
        System.out.println("we gave the values to the container!!!");

        container.getContainerProperty(id, "IMAGE").setValue(
                passProductId.getFilepath());
        try {
            container.commit();
            System.out.print("container is commited now !");
            conn.close();
            org.hsqldb.DatabaseManager.closeDatabases(0);
            Notification.show("success");
            getUI().getNavigator().navigateTo(
                    "main/select-all-items?restartApplication");

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
