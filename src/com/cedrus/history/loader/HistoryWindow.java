package com.cedrus.history.loader;

import com.cedrus.Main;
import com.cedrus.history.controller.HistoryController;
import com.cedrus.logger.ApplicationLogger;
import com.cedrus.models.Customer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HistoryWindow {

	private static final ApplicationLogger LOGGER = new ApplicationLogger(HistoryWindow.class);

	private Stage stage;
	private HistoryController controller;

	//<editor-fold desc="Class Constructor">
	public HistoryWindow() {
		stage = null;
		createWindow();
	}
	//</editor-fold>

	public void createWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(HistoryWindow.class.getResource("../view/history.fxml"));
			AnchorPane rootPane = loader.load();
			controller = loader.getController();
			Scene scene = new Scene(rootPane);
			scene.setFill(Color.TRANSPARENT);
			stage = new Stage(StageStyle.DECORATED);
			stage.setScene(scene);
			stage.setWidth(645);
			stage.setHeight(415+55);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(Main.getMainStage());
			stage.setTitle("History records");
			stage.setResizable(false);
			controller.setStage(stage);
		} catch (IOException e) {
			LOGGER.error("Error while loading window");
			LOGGER.debug(e.getMessage());
		}
	}

	public void showWindow(Customer customer) {
		controller.setModel(customer);
		stage.showAndWait();
	}	
}