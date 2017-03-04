package com.cedrus.ui.controls;

import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class CustomPasswordField extends PasswordField {

	private String escapeValue;
	private Pane focusPane;

	public CustomPasswordField() {
		super();
		this.getStylesheets().add(CustomTextField.class.getResource("styles/custom-text-field.css").toExternalForm());
	}

	public CustomPasswordField(String pass, AnchorPane focusPane) {
		this();
		this.focusPane = focusPane;
		this.setText(pass);
		initListeners();
	}
	
	public void reset(Pane focusPane) {
		this.focusPane = focusPane;
		initListeners();
	}
	
	private void initListeners() {
		this.setOnMouseClicked(e -> escapeValue = this.getText());
		this.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case ESCAPE:
				this.setText(escapeValue);
				event.consume();
				focusPane.requestFocus();
				break;
			case ENTER:
				event.consume();
				focusPane.requestFocus();
				break;
			default:
				break;
			}
		});
	}

}
