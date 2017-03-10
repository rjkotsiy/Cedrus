package com.cedrus.main.controller;

import com.cedrus.customer.loader.NewCustomerWindow;
import com.cedrus.db.DataBaseManager;
import com.cedrus.models.Customer;
import com.cedrus.ui.controls.CustomTextField;
import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import com.cedrus.utils.DateTimeUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private HBox customerProfileUpBar;
    //</editor-fold>

    private DataBaseManager dbManager;

    private ObservableList<Customer> customers;

    private Customer currentCustomerModel;
    private BooleanProperty dataNotValidated;
    private SmartButton updateCustomerInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createUI();
    }

    private void updateCustomer() {
        Customer customer = new Customer();

        customer.setId(currentCustomerModel.getId());
        customer.setFirstName(firstName.getText());
        customer.setLastName(secondName.getText());
        customer.setAddress(address.getText());
        customer.setBirthday(birthday.getValue().toString());
        customer.setGender(gender.getValue());
        customer.setPhone(phone.getText());
        customer.setDoctor(doctor.getText());
        customer.setDirection(direction.getText());

        dbManager.updateCustomer(customer);
        refreshCustomerTable();
        customersTable.getSelectionModel().select(currentCustomerModel);
        customersTable.requestFocus();
    }

    private void createUI() {
        SmartButton addNewCustomer = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Add Patient")
                .setHeight(20)
                .setWidth(110)
                .build();

        updateCustomerInfo = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Update data")
                .setHeight(20)
                .setWidth(100)
                .build();

        updateCustomerInfo.setOnAction(action -> {
            updateCustomer();
        });

        SmartButton addExamination = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Add Medical Examination")
                .setHeight(20)
                .setWidth(150)
                .build();

        SmartButton deleteCustomer = SmartButtonBuilder
                .getDefaultRedButtonBuilder()
                .setText("DELETE")
                .setHeight(20)
                .setWidth(80)
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

        customerProfileUpBar.getChildren().addAll(addExamination, updateCustomerInfo, deleteCustomer);
        HBox.setMargin(updateCustomerInfo, new Insets(0, 10, 0, 10));
        gender.getItems().add("MALE");
        gender.getItems().add("FEMALE");

    }


    public void setup() {
        dbManager = new DataBaseManager();
        dataNotValidated = new SimpleBooleanProperty(true);
        dbManager.connect();
        setupTableFactories();
        refreshCustomerTable();
        if (customers != null && !customers.isEmpty()) {
            customersTable.getSelectionModel().select(customers.get(0));
        }
        initializeControls();
    }

    private void initializeControls() {
        firstName.textProperty().addListener(e -> validateData());
        secondName.textProperty().addListener(e -> validateData());
        phone.textProperty().addListener(e -> validateData());
        address.textProperty().addListener(e -> validateData());
        birthday.valueProperty().addListener(e -> validateData());
        direction.textProperty().addListener(e -> validateData());
        gender.valueProperty().addListener(e -> validateData());
    }


    public void refreshCustomerTable() {
        customers = FXCollections.observableArrayList(dbManager.getCustomerList());
        customersTable.setItems(customers);
        customersTable.refresh();
    }

    private void setupTableFactories() {
        customerName.setCellValueFactory(cellData -> cellData.getValue().getCustomer());
        createCustomerNameFactory();
        createCustomerTableRowFactory();
    }

    private void validateData() {
        boolean emptyData = firstName.getText().trim().isEmpty()
                || secondName.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty()
                || address.getText().trim().isEmpty()
                || doctor.getText().trim().isEmpty()
                || direction.getText().trim().isEmpty()
                || birthday.getValue().toString().trim().isEmpty()
                || gender.getValue() == null;

        if (emptyData) {
            dataNotValidated.setValue(true);
            return;
        }
        boolean validationResult =
                (!currentCustomerModel.getFirstName().equals(firstName.getText()))
                        || (!currentCustomerModel.getLastName().equals(secondName.getText()))
                        || (!currentCustomerModel.getPhone().equals(phone.getText()))
                        || (!currentCustomerModel.getAddress().equals(address.getText()))
                        || (!currentCustomerModel.getDoctor().equals(doctor.getText()))
                        || (!currentCustomerModel.getDirection().equals(direction.getText()))
                        || (!birthday.getValue().toString().equals(currentCustomerModel.getBirthday()))
                        || (!gender.getValue().toString().equals(currentCustomerModel.getGender()));

        dataNotValidated.setValue(!validationResult);
    }

    private void setCustomerView(Customer customer) {
        if (customer != null) {
            updateCustomerInfo.disableProperty().unbind();

            currentCustomerModel = customer;

            firstName.setText(customer.getFirstName());
            secondName.setText(customer.getLastName());
            phone.setText(customer.getPhone());
            address.setText(customer.getAddress());
            birthday.setValue(DateTimeUtils.safeConvertToLocalDateTime(customer.getBirthday()).toLocalDate());
            gender.setValue(customer.getGender());
            doctor.setText(customer.getDoctor());
            direction.setText(customer.getDirection());

            dataNotValidated.setValue(true);

            updateCustomerInfo.disableProperty().bind(dataNotValidated);
        }
    }

    private void createCustomerTableRowFactory() {
        customersTable.setRowFactory(new Callback<TableView<Customer>, TableRow<Customer>>() {
            @Override
            public TableRow<Customer> call(TableView<Customer> param) {
                final TableRow<Customer> row = new TableRow<Customer>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);
                        setOnMouseClicked(e -> {
                            if (isSelected()) {
                                setStyle("-fx-background-color: #00b4d5; -fx-text-fill: #ffffff;");
                            } else {
                                setStyle(null);
                            }
                            setCustomerView(item);
                        });
                        selectedProperty().addListener(observable -> {
                            if (isSelected()) {
                                setStyle("-fx-background-color: #00b4d5; -fx-text-fill: #ffffff;");
                            } else {
                                setStyle(null);
                            }
                            setCustomerView(item);
                        });
                    }
                };
                return row;
            }
        });
    }


    //<editor-fold desc="Customer Table Cell Factories">
    private void createCustomerNameFactory() {


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
