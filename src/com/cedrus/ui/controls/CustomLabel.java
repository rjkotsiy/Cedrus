package com.cedrus.ui.controls;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class CustomLabel extends Label{

	public CustomLabel() {
		super();
		getStylesheets().add(CustomTextField.class.getResource("styles/custom-label.css").toExternalForm());
	}

	public CustomLabel(String text, Node graphic) {
		super(text, graphic);
		getStylesheets().add(CustomTextField.class.getResource("styles/custom-label.css").toExternalForm());
	}

	public CustomLabel(String text) {
		super(text);
		getStylesheets().add(CustomTextField.class.getResource("styles/custom-label.css").toExternalForm());
	}
	

}
