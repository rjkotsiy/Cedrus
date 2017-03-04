package com.cedrus.ui.controls;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

public class CustomTextArea extends TextArea {

	private static final int DEFAULT_MAX_TEXT_LENGTH = 9999;

	private String escapeValue;
	private AnchorPane focusPane;
	private int maxTextLength = DEFAULT_MAX_TEXT_LENGTH;

	public CustomTextArea() {
		super();
		applyStyle();
		initListeners();
	}

	public CustomTextArea(AnchorPane focusPane) {
		super();
		this.focusPane = focusPane;
		applyStyle();
		initListeners();
	}

	public CustomTextArea(String text, AnchorPane focusPane) {
		super(text);
		this.focusPane = focusPane;
		applyStyle();
		initListeners();
	}

	public void reset(AnchorPane focusPane) {
		this.focusPane = focusPane;
		applyStyle();
		initListeners();
	}

	public void setMaxTextLength(int maxValue) {
		maxTextLength = maxValue;
	}

	private void applyStyle() {
		this.getStylesheets().add(CustomTextField.class.getResource("styles/custom-text-area.css").toExternalForm());
	}

	private void initListeners() {
		this.setWrapText(true);
		this.setOnMouseClicked(e -> {
			if (this.getText() == null || this.getText().isEmpty()) {
				this.setText(" ");
				this.positionCaret(1);
				this.setText("");
			}
			escapeValue = this.getText();
		});

		this.setOnKeyPressed(event -> {
			if (!getSelectedText().isEmpty() && getSelectedText().length() == getText().length()
					&& (event.getCode().isDigitKey() || event.getCode().isLetterKey()) && !event.isControlDown()) {
				setText("");
			}
			if (event.getCode() == KeyCode.ESCAPE) {
				this.setText(escapeValue);
				event.consume();
				focusPane.requestFocus();
			} else if (event.getCode() == KeyCode.TAB) {
				TextAreaSkin skin = (TextAreaSkin) this.getSkin();
				if (skin.getBehavior() != null) {
					TextAreaBehavior behavior = skin.getBehavior();
					if (event.isControlDown()) {
						behavior.callAction("TraverseOrInsertTab");
					} else if (event.isShiftDown()) {
						behavior.callAction("TraversePrevious");
					} else {
						behavior.callAction("TraverseNext");
					}
					event.consume();
				}
			}
		});
	}

	@Override
	public void replaceText(int start, int end, String text) {
		if (text.equals("")) {
			super.replaceText(start, end, text);
		} else if (getText().length() < maxTextLength) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {

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
