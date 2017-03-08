package com.cedrus.ui.controls;

import com.cedrus.utils.OSDependStaff;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;

public class CustomTextField extends TextField {

	private static final int DEFAULT_MAX_TEXT_LENGTH = 50;

	private String escapeValue;
	private int maxTextLength = DEFAULT_MAX_TEXT_LENGTH;
	private Node parentNode;

	public CustomTextField() {
		super();
		setText("");
		escapeValue = getText();
		applyStyle();
		initListeners();
	}

	public CustomTextField(String text) {
		this();
		if (text == null) {
			text = "";
		}
		setText(text);

	}

	private void applyStyle() {
		getStylesheets().add(CustomTextField.class.getResource("../styles/css/custom-text-field.css").toExternalForm());
		if (OSDependStaff.isOsLinux()) {
			Font defaultFont = this.getFont();
			this.setFont(new Font(defaultFont.getName(), defaultFont.getSize() - 1.5));
		}
	}

	public void resetEscapeValue() {
		this.escapeValue = getText();
	}

	private void initListeners() {
		setOnMouseClicked(e -> {
			escapeValue = this.getText();
			parentNode = getParent();
			if (parentNode != null && getScene() != null) {
				getScene().setOnMousePressed(clickEvent -> parentNode.requestFocus());
			}
		});

		setOnKeyPressed(event -> {
			if (isEditable() && !getSelectedText().isEmpty() && getSelectedText().length() == getText().length()
					&& (event.getCode().isDigitKey() || event.getCode().isLetterKey()) && !event.isControlDown()) {
				setText("");
			}
			KeyCombination ctrlV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
			if (isEditable() && ctrlV.match(event) && !getSelectedText().isEmpty()
					&& getSelectedText().length() == getText().length()) {
				setText("");
			}
			switch (event.getCode()) {
			case ESCAPE:
				this.setText(escapeValue);
				event.consume();
				if (parentNode != null) {
					parentNode.requestFocus();
				}
				break;
			case ENTER:
				event.consume();
				if (parentNode != null) {
					parentNode.requestFocus();
				}
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void replaceText(int start, int end, String text) {
		if (this.isEditable()) {
			if (text.equals("")) {
				super.replaceText(start, end, text);
			} else if (getText().length() < maxTextLength) {
				super.replaceText(start, end, text);
			}
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (this.isEditable()) {
			if (text.equals("")) {
				super.replaceSelection(text);
			} else if (getText().length() < maxTextLength) {
				if (text.length() > maxTextLength - getText().length()) {
					text = text.substring(0, maxTextLength - getText().length());
				}
				super.replaceSelection(text);
			}
		}
	}

	public void setMaxTextLength(int maxTextLength) {
		this.maxTextLength = maxTextLength;
	}

}