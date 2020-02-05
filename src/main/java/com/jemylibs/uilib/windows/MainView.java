package com.jemylibs.uilib.windows;

import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.ctrls.ZMenu;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

import static com.jemylibs.uilib.UIController.assertation;


public class MainView implements ZFxml {
    static {
        assertation();
    }

    @FXML
    public VBox MainPane;

    @FXML
    public javafx.scene.control.MenuBar MenuBar;

    @FXML
    public TabPane MainStageTabPane;

    @FXML
    public Label app_title;
    public Parent view;

    @FXML
    public
    ImageView notificationImage;
    public HBox progressBarView;
    @FXML
    public
    ProgressBar progressBar;
    @FXML
    private HBox header_buttons;

    public static MainView getInstance() {
        return ZFxml.get("/zres/fx_layout/mainscene/main_scene.fxml");
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void init() {
        UpdateBackground();
    }

    public void UpdateBackground() {
        BackgroundImage myBI = new BackgroundImage(
                new Image("/zres/images/bg/11.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(-1, -1, true, true, false, true));
        MainPane.setBackground(new Background(myBI));
    }

    public void addMenu(Menu item) {
        this.MenuBar.getMenus().addAll(item);
    }

    public void addMenu(String name, ZMenu... menus) {
        this.MenuBar.getMenus().addAll(new Menu(name, null, menus));
    }

    @Override
    public void setView(Parent parent) {
        this.view = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainStageTabPane.getTabs().clear();
        header_buttons.getChildren().clear();
        header_buttons.setVisible(false);
        notificationImage.setVisible(false);
        progressBarView.setVisible(false);

    }

    public Button addHeaderButton(String text, EventHandler handler) {
        Button s = new Button(text);
        s.setOnAction(handler);
        header_buttons.getChildren().add(s);
        header_buttons.setVisible(header_buttons.getChildren().size() != 0);
        return s;
    }

    public Button addHeaderLabel(String text) {
        Button s = new Button(text);
        header_buttons.getChildren().add(s);
        header_buttons.setVisible(header_buttons.getChildren().size() != 0);
        return s;
    }

    public Button remove_headerButton(Button button) {
        header_buttons.getChildren().remove(button);
        header_buttons.setVisible(header_buttons.getChildren().size() != 0);
        return button;
    }


}
