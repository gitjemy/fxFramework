package com.jemylibs.uilib;

import com.jemylibs.data.seimpl.utility.IO;
import com.jemylibs.sedb.helpers.SQLiteHelper;
import com.jemylibs.sedb.utility.JDateTime;
import com.jemylibs.uilib.windows.MainView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

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
