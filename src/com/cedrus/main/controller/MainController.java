package com.cedrus.main.controller;

import com.cedrus.customer.loader.NewCustomerWindow;
import com.cedrus.db.DataBaseManager;
import com.cedrus.examination.loader.NewExaminationWindow;
import com.cedrus.history.loader.HistoryWindow;
import com.cedrus.langmanager.LangManager;
import com.cedrus.models.Customer;
import com.cedrus.ui.controls.*;
import com.cedrus.ui.messagebox.controller.MessageBoxController;
import com.cedrus.ui.messagebox.loader.MessageBoxWindow;
import com.cedrus.ui.resources.ResourceManager;
import com.cedrus.utils.DateTimeUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">

    @FXML
    private AnchorPane profileView;

    @FXML
    private CustomTextField firstName;

    @FXML
    private CustomTextField secondName;

    @FXML
    private CustomTextField phone;

    @FXML
    private CustomTextField address;

    @FXML
    private CustomDatePicker birthday;

    @FXML
    private CustomComboBox<String> gender;

    @FXML
    private CustomTextField doctor;

    @FXML
    private CustomTextArea direction;

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

    @FXML
    private HBox customerProfileBottomBar;

    @FXML
    private Label nextExaminationDateTime;
    //</editor-fold>

    private DataBaseManager dbManager;

    private ObservableList<Customer> customers;

    private Customer currentCustomerModel;
    private BooleanProperty dataNotValidated;

    private SmartButton updateCustomerInfo;
    private SmartButton addNewCustomer;
    private SmartButton addExamination;
    private SmartButton deleteCustomer;
    private SmartButton examinationHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createUI();
    }

    private void createUI() {

        LangManager.getInstance();

        SmartButton searchButton = SmartButtonBuilder
                .getLinkButtonBuilder()
                .setText(LangManager.getInstance().getSearchText(LangManager.Lang.EN))
                .setHeight(20)
                .setWidth(110)
                .setImage(ResourceManager.SEARCH)
                .build();

        addNewCustomer = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Add Patient")
                .setHeight(20)
                .setWidth(110)
                .build();

        examinationHistory = SmartButtonBuilder
                .getLinkButtonBuilder()
                .setText("History...")
                .setHeight(20)
                .setWidth(70)
                .build();

        examinationHistory.setOnAction(event -> {
            HistoryWindow historyWindow = new HistoryWindow();
            historyWindow.showWindow(currentCustomerModel);
        });

        updateCustomerInfo = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Update data")
                .setHeight(20)
                .setWidth(90)
                .build();


        updateCustomerInfo.setOnAction(action -> {
            updateCustomer();
        });

        addExamination = SmartButtonBuilder
                .getDefaultBlueButtonBuilder()
                .setText("Add Medical Examination")
                .setHeight(20)
                .setWidth(160)
                .build();

        addExamination.setOnAction(action -> {
            NewExaminationWindow examinationWindow = new NewExaminationWindow();
            examinationWindow.showWindow(currentCustomerModel);
        });

        deleteCustomer = SmartButtonBuilder
                .getDefaultRedButtonBuilder()
                .setText("x Delete")
                .setHeight(20)
                .setWidth(70)
                .build();

        deleteCustomer.setOnAction(event -> {
            MessageBoxWindow deleteMessageBox = new MessageBoxWindow();
            deleteMessageBox.createWindow();
            deleteMessageBox.setMessageBoxText("Are you sure want to delete profile?", "All profile data with examination history will be lost permanently!");
            MessageBoxController.MessageBoxResult messageBoxResult = deleteMessageBox.showWindow();
            if (messageBoxResult.equals(MessageBoxController.MessageBoxResult.CONFIRM)) {
                if (dbManager.deleteCustomer(currentCustomerModel)) {
                    refreshCustomerTable();
                    currentCustomerModel = null;
                    resetWindowState();
                }
            }
        });

        addNewCustomer.setOnAction(event -> {
            NewCustomerWindow newCustomerWindow = new NewCustomerWindow();
            newCustomerWindow.showWindow();
        });
        CustomTextField searchField = new CustomTextField();
        searchField.setPrefHeight(12);
        searchField.setMaxHeight(12);
        addNewCustomerHBox.getChildren().add(addNewCustomer);
        searchCustomerHBox.getChildren().add(searchButton);

        customerProfileUpBar.getChildren().addAll(addExamination, updateCustomerInfo, deleteCustomer);
        HBox.setMargin(updateCustomerInfo, new Insets(0, 15, 0, 15));

        customerProfileBottomBar.getChildren().add(examinationHistory);

        gender.getItems().add("MALE");
        gender.getItems().add("FEMALE");


    }

    public void resetWindowState() {

        profileView.setDisable(false);
        deleteCustomer.setDisable(false);

        updateCustomerInfo.disableProperty().unbind();
        updateCustomerInfo.setDisable(false);

        addExamination.setDisable(false);

        if (currentCustomerModel != null && customers != null && !customers.isEmpty()) {
            customersTable.getItems().forEach(item -> {
                if (item.getId().equals(currentCustomerModel.getId())) {
                    customersTable.getSelectionModel().select(item);
                    customersTable.requestFocus();
                    customersTable.refresh();
                    return;
                }
            });
        } else if (customers != null && !customers.isEmpty()) {
            customersTable.getSelectionModel().select(customers.get(0));
            customersTable.requestFocus();
            customersTable.refresh();
        } else {
            clearProfileView();
            profileView.setDisable(true);
            deleteCustomer.setDisable(true);
            updateCustomerInfo.setDisable(true);
            addExamination.setDisable(true);
        }
    }

    public DataBaseManager getDbManager() {
        return dbManager;
    }

    public void setup() {
        dbManager = new DataBaseManager();
        dataNotValidated = new SimpleBooleanProperty(true);
        dbManager.connect();
        setupTableFactories();
        refreshCustomerTable();
        resetWindowState();
        initializeControls();
    }

    private void initializeControls() {
        firstName.textProperty().addListener(e -> validateData());
        secondName.textProperty().addListener(e -> validateData());
        phone.textProperty().addListener(e -> validateData());
        address.textProperty().addListener(e -> validateData());
        birthday.dateProperty().addListener(e -> validateData());
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
                || birthday.getStringValue().toString().trim().isEmpty()
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
                        || (!birthday.getStringValue().toString().equals(currentCustomerModel.getBirthday()))
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
            nextExaminationDateTime.setText("Next assigned date time: " + customer.getNextExaminationDateTime());

            updateCustomerInfo.disableProperty().bind(dataNotValidated);
        }
    }

    //<editor-fold desc="Customer Table Factories">

    private void createCustomerTableRowFactory() {
        customersTable.setRowFactory(new Callback<TableView<Customer>, TableRow<Customer>>() {
            @Override
            public TableRow<Customer> call(TableView<Customer> param) {
                final TableRow<Customer> row = new TableRow<Customer>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        if (item != null) {
                            super.updateItem(item, empty);
                            setOnMouseClicked(e -> {
                                if (isSelected()) {
                                    setStyle("-fx-background-color: #00b4d5;");
                                } else {
                                    setStyle(null);
                                }
                                setCustomerView(item);
                                customersTable.refresh();
                            });

                            selectedProperty().addListener(observable -> {
                                if (isSelected()) {
                                    setStyle("-fx-background-color: #00b4d5;");
                                } else {
                                    setStyle(null);
                                }
                                setCustomerView(item);
                                customersTable.refresh();
                            });
                        }
                    }
                };
                return row;
            }
        });
    }

    private void createCustomerNameFactory() {

        customerName.setCellFactory(
                new Callback<TableColumn<Customer, Customer>, TableCell<Customer, Customer>>() {
                    @Override
                    public TableCell<Customer, Customer> call(
                            TableColumn<Customer, Customer> periodNameColumn) {
                        return new TableCell<Customer, Customer>() {
                            @Override
                            public void updateItem(Customer item, boolean empty) {
                                if (item != null) {
                                    super.updateItem(item, empty);
                                    setTextFill(Color.BLACK);
                                    setText(item.getFirstName() + " " + item.getLastName());
                                }
                            }
                        };
                    }
                });
    }

    //</editor-fold>

    private void updateCustomer() {
        Customer customer = new Customer();

        customer.setId(currentCustomerModel.getId());
        customer.setFirstName(firstName.getText());
        customer.setLastName(secondName.getText());
        customer.setAddress(address.getText());
        customer.setBirthday(birthday.getStringValue());
        customer.setGender(gender.getValue());
        customer.setPhone(phone.getText());
        customer.setDoctor(doctor.getText());
        customer.setDirection(direction.getText());

        dbManager.updateCustomer(customer);
        refreshCustomerTable();
        resetWindowState();
    }

    private void clearProfileView() {
        firstName.clear();
        secondName.clear();
        phone.clear();
        address.clear();
        birthday.dateProperty().setValue(null);
        gender.setValue(null);
        doctor.clear();
        direction.clear();
        dataNotValidated.setValue(true);
    }
}
