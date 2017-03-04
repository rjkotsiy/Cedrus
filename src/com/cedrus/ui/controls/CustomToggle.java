package com.cedrus.ui.controls;

import com.cedrus.ui.resources.ResourceManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustomToggle extends Button {

    private BooleanProperty onState = new SimpleBooleanProperty(true);

    private Image modeOn = ResourceManager.METRIC_COLLECTION_MODE_ON;
    private Image modeOff = ResourceManager.METRIC_COLLECTION_MODE_OFF;

    public CustomToggle() {
        getStylesheets().add(CustomToggle.class.getResource("styles/custom-toggle.css").toExternalForm());
        this.setPadding(Insets.EMPTY);
        this.setCursor(Cursor.HAND);
        initialize();
    }

    private void initialize() {
        this.setGraphic(new ImageView(modeOn));
        this.setOnAction(value -> {
            onState.setValue(!onState.getValue());
        });

        onState.addListener((listener, oldValue, newValue) -> {
            if (newValue) {
                this.setGraphic(new ImageView(modeOn));
            } else {
                this.setGraphic(new ImageView(modeOff));
            }
        });
    }

    public BooleanProperty stateProperty() {
        return onState;
    }
}
