package com.cedrus;

import com.cedrus.main.loader.Loader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String APP_VERISON = "v1.0 (alpha)";
    public static final String APPLICATION_TITLE = "Cedrus Customer Maintain " + APP_VERISON;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Loader mainLoader = new Loader();
        mainLoader.loadMainStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
