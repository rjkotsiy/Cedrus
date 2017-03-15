package com.cedrus.history.controller;

import com.cedrus.Main;
import com.cedrus.models.Customer;
import com.cedrus.models.Examination;
import com.cedrus.ui.controls.CustomLabel;
import com.cedrus.ui.controls.CustomTextArea;
import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    //<editor-fold desc="Mapped UI Controls">

    @FXML
    private HBox bottomToolbar;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private FlowPane flowPane;
    //</editor-fold>

    private Stage stage;

    private Customer customer;

    private List<Examination> records;

    private VBox createHistoryNode(Examination record) {
        VBox vbox = new VBox();

        HBox topInfoBar = new HBox();
        CustomLabel recordDate = new CustomLabel("Date time: " + record.getDate());
        CustomLabel doctorName = new CustomLabel("Doctor name: " + record.getDoctor());

        SmartButton attachedLinkButton = SmartButtonBuilder
                .getLinkButtonBuilder()
                .setText("Open design")
                .setHeight(19)
                .setPadding(new Insets(7, 0, 0, 0)).build();

        attachedLinkButton.setDisable(record.getAttachments() == null);

        topInfoBar.getChildren().addAll(recordDate, doctorName, attachedLinkButton);
        HBox.setMargin(doctorName, new Insets(0, 0, 0, 20));
        HBox.setMargin(attachedLinkButton, new Insets(0, 0, 0, 20));

        HBox summaryBar = new HBox();
        CustomTextArea summaryTextField = new CustomTextArea();
        summaryTextField.setText(record.getSummary());

        summaryTextField.setPrefHeight(60);
        summaryTextField.setPrefWidth(590);
        summaryBar.getChildren().addAll(summaryTextField);
        HBox.setMargin(summaryTextField, new Insets(0, 0, 0, 0));

        vbox.getChildren().addAll(topInfoBar, summaryBar);
        return vbox;
    }

    private void updateUI() {
        if (records != null && !records.isEmpty()) {
            records.forEach(record -> {
                flowPane.getChildren().add(createHistoryNode(record));
            });
        }
    }

    private void createControls() {
        double buttonHeight = 30;
        double buttonWidth = 70;

        SmartButton closeButton = SmartButtonBuilder.getDefaultTextButtonBuilder().setText("CLOSE")
                .setHeight(buttonHeight).setWidth(buttonWidth).build();
        closeButton.setFocusTraversable(true);
        closeButton.setOnAction(event -> {
            stage.close();
        });

        bottomToolbar.getChildren().add(closeButton);

        HBox.setMargin(closeButton, new Insets(20, 0, 0, 550));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createControls();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setModel(Customer customer) {
        this.customer = customer;
        records = Main.getMainController().getDbManager().getExaminations(customer.getId().toString());
        updateUI();
    }
}