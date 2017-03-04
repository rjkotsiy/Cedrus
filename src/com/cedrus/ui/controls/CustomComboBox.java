package com.cedrus.ui.controls;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class CustomComboBox<T> extends ComboBox<T> {

	private static final int DEFAULT_MAX_TEXT_LENGTH = 9999;

	private int maxTextLength = DEFAULT_MAX_TEXT_LENGTH;

	public CustomComboBox() {
		super();
		getStylesheets().add(CustomComboBox.class.getResource("styles/custom-combo-box.css").toExternalForm());
		addListeners();
	}

	private void addListeners() {
		getEditor().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.DELETE) {
				if (!getEditor().getSelectedText().isEmpty()) {
					// TODO KM: Add functionality to delete characters/selectedCharacters inside editor text
					getSelectionModel().clearSelection();
					getEditor().setText("");
				}

				event.consume();
			}
		});

		getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() > maxTextLength) {
				((StringProperty) observable).setValue(oldValue);
			}
		});
	}

	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

}
