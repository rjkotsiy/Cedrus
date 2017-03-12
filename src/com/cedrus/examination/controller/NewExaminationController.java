package com.cedrus.examination.controller;

import com.cedrus.Main;
import com.cedrus.models.Customer;
import com.cedrus.models.Examination;
import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class NewExaminationController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">

    @FXML
    private HBox bottomToolbar;

    @FXML
    private TextField doctorName;

    @FXML
    private DatePicker examinationDate;

    @FXML
    private TextArea examinationSummary;
    //</editor-fold>

    private SmartButton addButton;

    private Stage stage;

    private BooleanProperty dataNotConsistent;
    private Customer customer;

    private Examination createExaminationData() {
        Examination examination = new Examination();
        examination.setCustomerId(customer.getId());
        examination.setDoctor(doctorName.getText());
        examination.setSummary(examinationSummary.getText());
        examination.setDate(examinationDate.getValue().toString());
        return examination;
    }

    private void validateData() {
        boolean validationResult = doctorName.getText().trim().isEmpty()
                || examinationSummary.getText().trim().isEmpty()
                || examinationDate.getValue().toString().trim().isEmpty();

        dataNotConsistent.setValue(validationResult);
    }

    private void initializeControls() {
        examinationDate.setValue(LocalDate.now());
        doctorName.textProperty().addListener(e -> validateData());
        examinationSummary.textProperty().addListener(e -> validateData());
    }

    private void createControls() {
        double buttonHeight = 30;
        double buttonWidth = 70;

        addButton = SmartButtonBuilder.getDefaultBlueButtonBuilder().setText("ADD ").setHeight(buttonHeight)
                .setWidth(buttonWidth).setButtonDisabled(true).build();
        addButton.setFocusTraversable(true);

        addButton.setOnAction(event -> {
            if (Main.getMainController().getDbManager().addExamination(createExaminationData())) {
                Main.getMainController().refreshCustomerTable();
                Main.getMainController().resetWindowState();
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

        dataNotConsistent = new SimpleBooleanProperty(true);
        addButton.disableProperty().bind(dataNotConsistent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createControls();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        doctorName.setText(customer.getDoctor());
    }
}
