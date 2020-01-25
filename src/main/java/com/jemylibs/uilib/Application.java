package com.jemylibs.uilib;

import com.jemylibs.i18n.Constants;
import com.jemylibs.uilib.windows.MainView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class Application extends javafx.application.Application {
    static Application[] applications;
    private final Locale locale;
    ResourceBundle bundle;

    public Application(Locale locale) {
        bundle = ResourceBundle.getBundle(Constants.class.getName(), locale);
        this.locale = locale;
        applications = new Application[]{this};
    }

    public static Application getApplication() {
        return applications[0];
    }

    public static Application[] getApplications() {
        return applications;
    }

    public static String getResourceString(String key) {
        return getApplication().getBundle().getString(key);
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

    public Locale getLocale() {
        return locale;
    }
}
