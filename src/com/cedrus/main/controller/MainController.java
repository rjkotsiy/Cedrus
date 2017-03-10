package com.cedrus.main.controller;

import com.cedrus.customer.loader.NewCustomerWindow;
import com.cedrus.db.DataBaseManager;
import com.cedrus.models.Customer;
import com.cedrus.ui.controls.CustomTextField;
import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import com.cedrus.ui.resources.ResourceManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">
    @FXML
    private TextField firstName;

    @FXML
    private TextField secondName;

    @FXML
    private TextField phone;

    @FXML
    private TextField address;

    @FXML
    private DatePicker birthday;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private TextField doctor;

    @FXML
    private TextArea direction;

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Customer> customerName;

    @FXML
    private HBox addNewCustomerHBox;

    @FXML
    private HBox searchCustomerHBox;
    //</editor-fold>

    private DataBaseManager dbManager;

    private ObservableList<Customer> customers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createUI();
    }

    private void createUI() {
        SmartButton addNewCustomer = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Add Patient")
                .setHeight(20)
                .setWidth(110)
                .build();

        addNewCustomer.setOnAction(event -> {
            NewCustomerWindow newCustomerWindow = new NewCustomerWindow();
            newCustomerWindow.showWindow();
        });
        CustomTextField searchField = new CustomTextField();
        searchField.setPrefHeight(12);
        searchField.setMaxHeight(12);
        addNewCustomerHBox.getChildren().add(addNewCustomer);
        searchCustomerHBox.getChildren().add(0, searchField);
    }


    public void setup() {
        dbManager = new DataBaseManager();
        dbManager.connect();
        setupTableFactories();
        refreshCustomerTable();
    }

    public void refreshCustomerTable() {
        customers = FXCollections.observableArrayList(dbManager.getCustomerList());
        customersTable.setItems(customers);
        customersTable.refresh();
    }

    private void setupTableFactories() {
        customerName.setCellValueFactory(cellData -> cellData.getValue().getCustomer());
        createEditCustomerFactory();
    }

    //<editor-fold desc="Customer Table Cell Factories">
    private void createEditCustomerFactory() {


        customerName.setCellFactory(
                new Callback<TableColumn<Customer, Customer>, TableCell<Customer, Customer>>() {
                    @Override
                    public TableCell<Customer, Customer> call(
                            TableColumn<Customer, Customer> periodNameColumn) {

                        return new TableCell<Customer, Customer>() {

                            @Override
                            public void updateItem(Customer item, boolean empty) {
                                if (item != null && !empty) {
                                    super.updateItem(item, empty);
                                    setText(item.getFirstName() + " " + item.getLastName());

//                                    SmartButton addNewCustomer = SmartButtonBuilder
//                                            .getDefaultBlueButtonBuilder()
//                                            .setText("EDIT")
//                                            .setHeight(20)
//                                            .setWidth(50)
//                                            .build();

//                                    setGraphic(addNewCustomer);
                                }
                            }
                        };
                    }
                });
    }

    //</editor-fold>

    public DataBaseManager getDbManager() {
        return dbManager;
    }
}
