package com.cedrus.customer.loader;

import com.cedrus.Main;
import com.cedrus.customer.controller.NewCustomerController;
import com.cedrus.logger.ApplicationLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewCustomerWindow {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(NewCustomerWindow.class);

	private Stage stage;
	private NewCustomerController controller;

	//<editor-fold desc="Class Constructor">
	public NewCustomerWindow() {
		stage = null;
		createWindow();
	}
	//</editor-fold>

	public void createWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(NewCustomerWindow.class.getResource("../view/NewCustomer.fxml"));
			AnchorPane rootPane = loader.load();
			controller = loader.getController();
			Scene scene = new Scene(rootPane);
			scene.setFill(Color.TRANSPARENT);
			stage = new Stage(StageStyle.DECORATED);
			stage.setScene(scene);
			stage.setWidth(637);
			stage.setHeight(586);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Main.getMainStage());
			stage.setTitle("Create Patient Profile");
			stage.setResizable(false);
			controller.setStage(stage);
		} catch (IOException e) {
			LOGGER.error("Error while loading LocalObject window");
			LOGGER.debug(e.getMessage());
		}
	}

	public void showWindow() {
		stage.showAndWait();
	}	
}