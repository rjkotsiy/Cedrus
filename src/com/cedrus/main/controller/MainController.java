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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, ImageView> customerMenu;

    @FXML
    private HBox addNewCustomerHBox;

    @FXML
    private HBox searchCustomerHBox;

    private DataBaseManager dbManager = new DataBaseManager();

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
        searchField.setPrefHeight(18);
        addNewCustomerHBox.getChildren().add(addNewCustomer);
        searchCustomerHBox.getChildren().add(0, searchField);
    }


    public void setup() {
        dbManager = new DataBaseManager();
        dbManager.connect();
        createTableCellValueFactorys();
        customers = FXCollections.observableArrayList(dbManager.getCustomerList());
        customersTable.setItems(customers);
        customersTable.refresh();
    }

    private void createTableCellValueFactorys() {
        customerName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        customerMenu.setCellValueFactory(cellData -> new SimpleObjectProperty<ImageView>(new ImageView(ResourceManager.METRIC_COLLECTION_MODE_OFF)));
    }


}
