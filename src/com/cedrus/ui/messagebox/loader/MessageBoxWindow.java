package com.cedrus.ui.messagebox.loader;

import com.cedrus.Main;
import com.cedrus.logger.ApplicationLogger;
import com.cedrus.ui.messagebox.controller.MessageBoxController;
import com.cedrus.ui.styles.CssEffectsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MessageBoxWindow {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(MessageBoxWindow.class);

	private Stage stage;
	private MessageBoxController controller;

	/**
	 * Class constructor
	 */
	public MessageBoxWindow() {
		stage = null;
	}

	public void createWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MessageBoxWindow.class.getResource("../view/MessageBox.fxml"));
			AnchorPane shadowPane = loader.load();
			AnchorPane viewPane = (AnchorPane) shadowPane.lookup("#mainPane");
			viewPane.setStyle(CssEffectsManager.SHADOW_EFFECT);
			shadowPane.setStyle("-fx-background-color: transparent;");
			controller = loader.getController();

			Scene scene = new Scene(shadowPane);			
			scene.setFill(Color.TRANSPARENT);
			stage = new Stage(StageStyle.UNDECORATED);
			stage.initStyle(StageStyle.TRANSPARENT);
			
			stage.setScene(scene);
			controller.setStage(stage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Main.getMainStage());
			stage.setTitle(Main.APPLICATION_TITLE);

			controller.focusCancelButton();
			
		} catch (IOException e) {
			LOGGER.error("Error while loading MessageBox window " + e);
			LOGGER.debug(e.getMessage());
		}
	}

	public void setMessageBoxText(String title, String description) {
		controller.setMessageBoxText(title, description);
	}

	public void setMessageBoxButton(String confirmBtnName, String cancelBtnName) {
		controller.setMessageBoxButtonNames(confirmBtnName, cancelBtnName);
	}
	
	public void setConfirmButtonWidth(double width) {
		controller.setConfirmButtonWidth(width);
	}

	public MessageBoxController.MessageBoxResult showWindow() {
		stage.showAndWait();
		return controller.getMessageBoxResult();
	}

}
