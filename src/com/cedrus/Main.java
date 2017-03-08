package com.cedrus;

import com.cedrus.main.controller.MainController;
import com.cedrus.main.mainloader.MainStageLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String APP_VERISON = "v1.0 (alpha)";
    public static final String APPLICATION_TITLE = "Cedrus Patient Profiles " + APP_VERISON;

    public static Stage mainStage;

    private static MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainStageLoader mainLoader = new MainStageLoader();
        mainLoader.loadMainStage(primaryStage);
        mainController = mainLoader.getMainController();
        mainStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static MainController getMainController() {
        return mainController;
    }

}
