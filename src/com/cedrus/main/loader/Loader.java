package com.cedrus.main.loader;

import com.cedrus.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loader {

    public void loadMainStage(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        primaryStage.setTitle(Main.APPLICATION_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

}
