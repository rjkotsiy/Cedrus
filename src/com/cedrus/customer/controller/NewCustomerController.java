package com.cedrus.customer.controller;

import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCustomerController implements Initializable{

    @FXML
    private HBox bottomToolbar;

    private SmartButton addButton;

    private Stage stage;

    private void createBottomToolbar() {
        double buttonHeight = 30;
        double buttonWidth = 70;

        addButton = SmartButtonBuilder.getDefaultBlueButtonBuilder().setText("ADD ").setHeight(buttonHeight)
                .setWidth(buttonWidth).build();
        addButton.setFocusTraversable(true);

        //addButton.setOnAction(event -> handleAddLocalProfileButton());

        SmartButton cancelButton = SmartButtonBuilder.getDefaultTextButtonBuilder().setText("CANCEL")
                .setHeight(buttonHeight).setWidth(buttonWidth).build();
        cancelButton.setFocusTraversable(true);
        cancelButton.setOnAction(event -> stage.close());

        bottomToolbar.getChildren().add(cancelButton);
        bottomToolbar.getChildren().add(addButton);

        HBox.setMargin(cancelButton, new Insets(20, 0, 0, 445));
        HBox.setMargin(addButton, new Insets(20, 0, 0, 10));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBottomToolbar();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
