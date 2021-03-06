package com.jemylibs.uilib.utilities.alert;

import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Toast implements ZFxml {
    public static final String ErrorStyleClass = "type-error";
    public static final String FineStyleClass = "type-fine";

    static ArrayList<Tooltip> toasts = new ArrayList<>();
    @FXML
    public Button close_but;
    Parent parent;
    @FXML
    private Label title_label, content_label;

    public static Tooltip SucssesToast(String Title, String Txt) {
        return JToast(Title, Txt, FineStyleClass, Duration.seconds(10));
    }

    public static Tooltip ErrorToast(String Title, String Txt) {
        return JToast(Title, Txt, ErrorStyleClass, Duration.seconds(18));
    }

    public static Tooltip JToast(String Title, String Text, String styleClass, Duration duration) {
        Stage mainStage = Application.getApplication().getUiController().getMainStage();
        if (mainStage == null) {
            System.out.println(Title + " - " + Text);
            return null;
        }
        Toast toast = ZFxml.get("/zres/fx_layout/alerts/toast.fxml");

        Tooltip tooltip = new Tooltip();
        toasts.add(tooltip);
        tooltip.setStyle("-fx-background-color: rgba(0,0,0,.0)");
        tooltip.setGraphic(toast.parent);
        toast.fill(styleClass, Title, Text);

        toast.close_but.setOnAction(event -> tooltip.hide());
        toast.close_but.setGraphic(FontAwesome.FA_CLOSE.mk(10, Color.WHITE));

        if (mainStage.isShowing()) {
            tooltip.show(mainStage, mainStage.getX() + 20, mainStage.getY() + 20);
        } else {
            tooltip.show(mainStage, 50, 50);
        }

        //////Show and Hide
        //////Still showing in Hover Support
        SimpleBooleanProperty HoveProperty = new SimpleBooleanProperty(false);
        toast.parent.setOnMouseEntered(v -> HoveProperty.set(true));
        toast.parent.setOnMouseExited(v -> HoveProperty.set(false));

        PauseTransition wait = new PauseTransition(duration);
        wait.setOnFinished((e) -> {
            if (HoveProperty.get()) {
                wait.play();
            } else {
                tooltip.hide();
                toasts.remove(tooltip);
            }
        });
        wait.play();

        HoveProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            wait.playFromStart();
        });
        return tooltip;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
        parent.setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        if (Application.getApplication().getUiController().isDebugcss()) {
            parent.getStylesheets().setAll("file:///D:/Workstation/NEWSYSTEMS/fxframework/src/main/resources/" + "zres/fx_layout/alerts/toast.css");
        }
    }

    public void fill(String styleClass, String title, String content) {
        this.title_label.setText(title);
        this.content_label.setText(content);
        parent.getStyleClass().add(styleClass);
    }

}
