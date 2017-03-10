package com.cedrus.customer.controller;

import com.cedrus.Main;
import com.cedrus.models.Customer;
import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">

    @FXML
    private HBox bottomToolbar;

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
    //</editor-fold>

    private SmartButton addButton;

    private Stage stage;

    private BooleanProperty dataNotCostistent;

    private Customer createCustomerData() {
        Customer customer = new Customer();
        customer.setFirstName(firstName.getText());
        customer.setLastName(secondName.getText());
        customer.setAddress(address.getText());
        customer.setBirthday(birthday.getValue().toString());
        customer.setGender(gender.getValue());
        customer.setPhone(phone.getText());
        customer.setRegistration_date(LocalDate.now().toString());
        customer.setDoctor(doctor.getText());
        customer.setDirection(direction.getText());
        return customer;
    }

    private void validateData() {
        boolean validationResult = firstName.getText().trim().isEmpty()
                || secondName.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty()
                || address.getText().trim().isEmpty()
                || doctor.getText().trim().isEmpty()
                || direction.getText().trim().isEmpty()
                || birthday.getValue().toString().trim().isEmpty()
                || gender.getValue() == null;

        dataNotCostistent.setValue(validationResult);
    }

    private void initializeControls() {
        birthday.setValue(LocalDate.now());

        gender.getItems().add("MALE");
        gender.getItems().add("FEMALE");

        gender.valueProperty().addListener(e -> validateData());
        firstName.textProperty().addListener(e -> validateData());
        secondName.textProperty().addListener(e -> validateData());
        phone.textProperty().addListener(e -> validateData());
        address.textProperty().addListener(e -> validateData());
        birthday.valueProperty().addListener(e -> validateData());
        direction.textProperty().addListener(e -> validateData());

    }

    private void createControls() {
        double buttonHeight = 30;
        double buttonWidth = 70;

        addButton = SmartButtonBuilder.getDefaultBlueButtonBuilder().setText("ADD ").setHeight(buttonHeight)
                .setWidth(buttonWidth).setButtonDisabled(true).build();
        addButton.setFocusTraversable(true);

        addButton.setOnAction(event -> {
            if (Main.getMainController().getDbManager().addCustomer(createCustomerData())) {
                Main.getMainController().refreshCustomerTable();
                stage.close();
            }
        });

        SmartButton cancelButton = SmartButtonBuilder.getDefaultTextButtonBuilder().setText("CANCEL")
                .setHeight(buttonHeight).setWidth(buttonWidth).build();
        cancelButton.setFocusTraversable(true);
        cancelButton.setOnAction(event -> stage.close());

        bottomToolbar.getChildren().add(cancelButton);
        bottomToolbar.getChildren().add(addButton);

        HBox.setMargin(cancelButton, new Insets(20, 0, 0, 445));
        HBox.setMargin(addButton, new Insets(20, 0, 0, 10));

        initializeControls();

        dataNotCostistent = new SimpleBooleanProperty(true);
        addButton.disableProperty().bind(dataNotCostistent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createControls();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
