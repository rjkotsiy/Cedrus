package com.cedrus.ui.messagebox.controller;

import com.cedrus.ui.controls.SmartButton;
import com.cedrus.ui.controls.SmartButtonBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MessageBoxController {

	//<editor-fold desc="FXML - mapped UI controls">
	@FXML
	private AnchorPane shadowPane;
	
	@FXML
	private AnchorPane mainPane;

	@FXML
	private Label descriptionLabel;
	
	@FXML
	private Label titleLabel;
	@FXML
	private HBox buttons;
	//</editor-fold>

	private double initialX;
	private double initialY;
	
	private Stage stage;
	private SmartButton confirmButton;
	private SmartButton cancelButton;

	private MessageBoxResult messageBoxResult;

	@FXML
	private void handleCancel() {
		messageBoxResult = MessageBoxResult.CANCEL;
		stage.close();
	}
	
	@FXML
	private void handleConfirm() {
		messageBoxResult = MessageBoxResult.CONFIRM;
		stage.close();
	}
		
	@FXML
	private void initialize(){
		descriptionLabel.setWrapText(true);
		descriptionLabel.setMaxWidth(320);
		descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
		addDraggablePane(mainPane);
		createButtons();

		shadowPane.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ESCAPE)) {
				handleCancel();
			}
		});
	//	Controls.checkShadowState(mainPane);
	}

	private void createButtons() {
		double buttonsHeight = 40;
		double buttonsWidth = 100;
		double fontSize = 16;

		confirmButton = SmartButtonBuilder.getDefaultBlueButtonBuilder()
				.setText("DELETE")
				.setFontSize(fontSize)
				.setWidth(buttonsWidth)
				.setHeight(buttonsHeight)
				.build();
		confirmButton.setOnAction(event -> handleConfirm());
		confirmButton.setFocusTraversable(true);
		buttons.getChildren().add(confirmButton);

		cancelButton = SmartButtonBuilder.getDefaultTextButtonBuilder()
				.setText("CANCEL")
				.setFontSize(fontSize)
				.setWidth(buttonsWidth)
				.setHeight(buttonsHeight)
				.build();
		cancelButton.setFocusTraversable(true);
		cancelButton.setOnAction(event -> handleCancel());
		buttons.getChildren().add(cancelButton);
		HBox.setMargin(cancelButton, new Insets(0, 0, 0, 10));
	}
	
	private void addDraggablePane(AnchorPane dragAnchorPane) {
		dragAnchorPane.setOnMousePressed(me -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                initialX = me.getSceneX();
                initialY = me.getSceneY();
            }
        });
		dragAnchorPane.setOnMouseDragged(me -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                dragAnchorPane.getScene().getWindow().setX(me.getScreenX() - initialX);
                dragAnchorPane.getScene().getWindow().setY(me.getScreenY() - initialY);
            }
        });
	}
	
	public enum MessageBoxResult {CONFIRM, CANCEL}

    public void setStage(Stage stage){
		this.stage = stage;	
	}
	
	public void setConfirmButtonWidth(double width) {
		if (confirmButton != null) {
			confirmButton.setPrefWidth(width);
		}
	}
	
	public void setMessageBoxText(String title, String description) {
		titleLabel.setText(title);
		descriptionLabel.setText(description);
	}

	public void setMessageBoxButtonNames(String confirmButtonName, String cancelButtonName) {
		confirmButton.setText(confirmButtonName);
		if (cancelButtonName == null || cancelButtonName.trim().isEmpty()) {
			buttons.getChildren().remove(cancelButton);
		} else {
			cancelButton.setText(cancelButtonName);
		}
	}
	
	public MessageBoxResult getMessageBoxResult() {
		return messageBoxResult;
	}

	public void focusCancelButton() {
		cancelButton.requestFocus();
	}
	
}
