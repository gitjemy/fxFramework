package com.jemylibs.uilib.utilities.alert;

import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.ZView.ZFxml;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Toast implements ZFxml {
    @FXML
    public Button close_but;

    Parent parent;
    @FXML
    private Label title_label, content_label;


    public static Tooltip SucssesToast(String Title, String Txt) {
        return JToast(Title, Txt, Type.Fine, Duration.seconds(10));
    }

    public static Tooltip ErrorToast(String Title, String Txt) {
        return JToast(Title, Txt, Type.Error, Duration.seconds(18));
    }

    public static Tooltip JToast(String Title, String Text, Type toast_type, Duration duration) {
        Stage mainStage = UIController.mainStage;
        if (mainStage == null) {
            System.out.println(Title + " - " + Text);
            return null;
        }
        Toast toast = ZFxml.get("/zres/fx_layout/alerts/toast.fxml");

        Tooltip S = new Tooltip();
        S.setStyle("-fx-background-color: rgba(0,0,0,.0)");
        S.setGraphic(toast.parent);
        toast.fill(toast_type, Title, Text);

        toast.close_but.setOnAction(event -> S.hide());
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double y = screenBounds.getMinY() + screenBounds.getHeight();
        if (mainStage.isShowing()) {
            S.show(mainStage, mainStage.getX() + 20, mainStage.getY() + 20);
        } else {
            S.show(mainStage, 50, 50);
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
                S.hide();
            }
        });
        wait.play();

        HoveProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            wait.playFromStart();
        });
        return S;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
    }

    public void fill(Type type, String title, String content) {
        this.title_label.setText(title);
        this.content_label.setText(content);
        parent.getStyleClass().add(type.style);
    }

    public enum Type {
        Error("type-error", "حدث خطأ"), Fine("type-fine", "تم");
        final String style;
        final String text;

        Type(String style, String text) {
            this.style = style;
            this.text = text;
        }
    }
}
