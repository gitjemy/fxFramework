package com.jemylibs.uilib;

import com.jemylibs.uilib.windows.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIController.mainStage = primaryStage;
        UIController.mainView = MainView.getInstance();

        configureMainView(UIController.mainView);
        primaryStage.setScene(new Scene(UIController.mainView.view));
        startApp(UIController.mainStage);
    }

    protected abstract void configureMainView(MainView mainView);

    protected abstract void startApp(Stage stage);
}
