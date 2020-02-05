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
    private UIController uiController;

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

    public UIController getUiController() {
        return uiController;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        uiController = UIController.getInstance(primaryStage);
        configureMainView(uiController.getMainView());
        primaryStage.getIcons().setAll(new Image("/zres/images/Icons/app-icon.png"));
        uiController.getMainView().MenuBar.getMenus().clear();
        startApp(primaryStage);
    }

    protected abstract void configureMainView(MainView mainView);

    protected abstract void startApp(Stage stage);

    public Locale getLocale() {
        return locale;
    }

    public void addTask(UIController.Task task) {
        uiController.addTask(task);
    }
}
