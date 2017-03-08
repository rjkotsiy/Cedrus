package com.cedrus.main.mainloader;

import com.cedrus.Main;
import com.cedrus.main.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStageLoader {

    public void loadMainStage(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainStageLoader.class.getResource("../view/main.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        primaryStage.setTitle(Main.APPLICATION_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 660, 600));
        primaryStage.show();
        MainController mainController = loader.getController();
        mainController.setup();
    }

}
