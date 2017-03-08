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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">
    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, ImageView> editCustomer;

    @FXML
    private TableColumn<Customer, ImageView> addCustomerHistory;

    @FXML
    private TableColumn<Customer, ImageView> deleteCustomer;

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
        searchField.setPrefHeight(18);
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
        customerName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        editCustomer.setCellValueFactory(cellData -> new SimpleObjectProperty<>(ResourceManager.NULL_IMAGE_VIEW));
        addCustomerHistory.setCellValueFactory(cellData -> new SimpleObjectProperty<>(ResourceManager.NULL_IMAGE_VIEW));

        createEditCustomerFactory();
        createAddCustomerHistoryFactory();
    }

    //<editor-fold desc="Customer Table Cell Factories">
    private void createEditCustomerFactory() {

        editCustomer.setCellFactory(
                new Callback<TableColumn<Customer, ImageView>, TableCell<Customer, ImageView>>() {
                    @Override
                    public TableCell<Customer, ImageView> call(
                            TableColumn<Customer, ImageView> periodNameColumn) {

                        return new TableCell<Customer, ImageView>() {

                            @Override
                            public void updateItem(ImageView item, boolean empty) {
                                if (item != null && !empty) {
                                    super.updateItem(item, empty);
                                    SmartButton addNewCustomer = SmartButtonBuilder
                                            .getDefaultBlueButtonBuilder()
                                            .setText("EDIT")
                                            .setHeight(20)
                                            .setWidth(50)
                                            .build();
                                    setGraphic(addNewCustomer);
                                }
                            }
                        };
                    }
                });

    }

    private void createAddCustomerHistoryFactory() {

        addCustomerHistory.setCellFactory(
                new Callback<TableColumn<Customer, ImageView>, TableCell<Customer, ImageView>>() {
                    @Override
                    public TableCell<Customer, ImageView> call(
                            TableColumn<Customer, ImageView> periodNameColumn) {

                        return new TableCell<Customer, ImageView>() {

                            @Override
                            public void updateItem(ImageView item, boolean empty) {
                                if (item != null && !empty) {
                                    super.updateItem(item, empty);
                                    SmartButton addNewCustomer = SmartButtonBuilder
                                            .getDefaultBlueButtonBuilder()
                                            .setText("HISTORY")
                                            .setHeight(20)
                                            .setWidth(60)
                                            .build();
                                    setGraphic(addNewCustomer);
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
