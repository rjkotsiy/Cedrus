package com.cedrus.ui.controls;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collection;

/*
* @file SearchableComboBox.java
* 
* @note Copyright (c) 2015 SoftServe, Inc. Confidential and proprietary. All
*       rights reserved.
*
* @author  (panten@softserveinc.com)
* @since 2016-08-24
*/
public class SearchableComboBox<T> extends ComboBox<T>{

	public SearchableComboBox() {
		super();
		getStylesheets().add(CustomComboBox.class.getResource("styles/searchable-combo-box.css").toExternalForm());
		initListeners();
		this.setEditable(true);
	}
	
	private void initListeners(){
		focusedProperty().addListener((listener, oldValue, newValue) -> {
			if (newValue) {
				this.show();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public void initSearchAbility(ObservableList<String> observableFields) {

		this.addEventHandler(KeyEvent.KEY_RELEASED, event -> {

			String typedText = this.getEditor().getText();

			if(!event.isControlDown() && (event.getCode().isLetterKey() || event.getCode().isDigitKey())) {
				this.show();
			}

			if (!this.isShowing() && event.getCode() == KeyCode.DOWN) {
				this.show();
			}

			if (this.getEditor().getSelectedText().length() == typedText.length()) {
				this.getSelectionModel().clearSelection();
				this.getEditor().setText(typedText);
				this.getEditor().selectAll();
			}

			if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
				int pos = this.getEditor().getCaretPosition();
				this.getItems().setAll((Collection<? extends T>) observableFields.filtered(field -> field.toLowerCase().contains(typedText.toLowerCase())));
				this.getEditor().setText(typedText);
				this.getEditor().positionCaret(pos);
				this.hide();
				this.show();
			}

			if ( (event.getCode().isLetterKey() || event.getCode().isDigitKey()) || event.getCode().isWhitespaceKey()) {
				int pos = this.getEditor().getCaretPosition();
				IndexRange selection = this.getEditor().getSelection();
				this.getItems().setAll((Collection<? extends T>) observableFields.filtered(field -> field.toLowerCase().contains(typedText.toLowerCase())));
				this.getEditor().setText(typedText);
				this.getEditor().positionCaret(pos);
				this.getEditor().selectRange(selection.getStart(), selection.getEnd());
				if(!event.isControlDown()) {
					this.hide();
					this.show();
				}
			} 
			if (event.getCode() == KeyCode.ENTER) {
				this.hide();
			}
		});
	}
}
