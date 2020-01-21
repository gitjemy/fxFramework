package com.jemylibs.uilib;

import com.jemylibs.uilib.windows.MainView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Application extends javafx.application.Application {
    static Application[] applications;

    ResourceBundle bundle;

    public Application(Locale locale) {
        bundle = ResourceBundle.getBundle("com.jemylibs.i18n.Constants", locale);
        applications = new Application[]{this};
    }

    public static Application getApplication() {
        return applications[0];
    }

    public static Application[] getApplications() {
        return applications;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIController.mainStage = primaryStage;
        UIController.mainView = MainView.getInstance();
        configureMainView(UIController.mainView);
        primaryStage.setScene(UIController.createScene(UIController.mainView.view));
        UIController.mainStage.getIcons().setAll(new Image("/zres/images/Icons/app-icon.png"));

        UIController.mainView.MenuBar.getMenus().clear();
        startApp(UIController.mainStage);
    }

    protected abstract void configureMainView(MainView mainView);

    protected abstract void startApp(Stage stage);

}
