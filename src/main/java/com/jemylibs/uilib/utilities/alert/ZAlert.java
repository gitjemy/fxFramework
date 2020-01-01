package com.jemylibs.uilib.utilities.alert;

import com.jemylibs.data.seimpl.utility.ZSystemError;
import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.utilities.ZValidate;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public interface ZAlert {

    static void errorHandle(Throwable error) {
        error.printStackTrace();
        try {
            if (!(error instanceof ZValidate)) {
                error.printStackTrace();
            } else {
                System.out.println(error.getMessage());
            }
            if (error instanceof ZValidate) {
                ZValidate Er = (ZValidate) error;
                Tooltips.ShowErrorTip(Er.getNode(), Er.getMessage());
            } else {
                String title;
                if (error instanceof ZSystemError) {
                    title = ((ZSystemError) error).getTitle();
                } else {
                    title = "حدث خطأ";
                }
                if (UIController.mainStage != null && UIController.mainStage.isShowing()) {
                    Toast.ErrorToast(title, error.getMessage());
                } else {
                    Dialog.ErrorAlert(error.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void errorHandle(Throwable Error, Label errorLabel) {
        if (Error instanceof ZValidate) {
            errorHandle(Error);
        } else {
            errorHandle(new ZValidate(errorLabel, Error.getMessage()));
        }
    }

    interface Tooltips {

        //<editor-fold defaultstate="collapsed" desc="Methods">
        static void ShowErrorTip(Node node, String Text) {
            if (node == null) {
                Toast.ErrorToast("خطأ", Text);
            } else if (node.getScene() == null) {
                Toast.ErrorToast(node.getAccessibleText(), Text);
                return;
            }

            node.requestFocus();
            Tooltip S = new Tooltip(Text);
            S.getStyleClass().add("ErrorTo olTip");
            Bounds screen = node.localToScreen(node.getBoundsInLocal());
            double CX = screen.getMaxX();
            double CY = screen.getMinY();
            S.show(node, CX, CY - 40);
            double area = screen.getWidth() * screen.getHeight();
            if (area < 20000) {
                FadeTransition FT = new FadeTransition(Duration.millis(180), node);
                FT.setFromValue(.7);
                FT.setToValue(1);
                FT.setCycleCount(5);
                FT.setAutoReverse(true);
                FT.play();
            }
            node.setStyle("-fx-focus-color: red;");
            PauseTransition wait = new PauseTransition(Duration.seconds(5));
            wait.setOnFinished((e) -> {
                S.hide();
                node.setStyle(null);
            });
            wait.play();
        }

        static void ShowGuideTip(Node node, String Text) {
            if (node.getScene() == null) {
                Toast.ErrorToast(node.getAccessibleText(), Text);
                return;
            }
            node.requestFocus();
            Tooltip S = new Tooltip(Text);
            S.getStyleClass().add("GuideToolTip");
            Bounds boundsInScreen = node.localToScreen(node.getBoundsInLocal());
            double CX = boundsInScreen.getMaxX();
            double CY = boundsInScreen.getMinY();
            S.show(node, CX, CY - 40);
            double area = boundsInScreen.getWidth() * boundsInScreen.getHeight();
            if (area < 20000) {
                FadeTransition FT = new FadeTransition(Duration.millis(180), node);
                FT.setFromValue(.7);
                FT.setToValue(1);
                FT.setCycleCount(5);
                FT.setAutoReverse(true);
                FT.play();
            }
            node.setStyle("-fx-focus-color: Black;");
            PauseTransition wait = new PauseTransition(Duration.seconds(6));
            wait.setOnFinished((e) -> {
                S.hide();
                node.setStyle(null);
            });
            wait.play();
        }

//</editor-fold>
    }

}
