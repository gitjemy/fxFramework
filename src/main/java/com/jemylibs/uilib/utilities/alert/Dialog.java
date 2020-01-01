package com.jemylibs.uilib.utilities.alert;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialog {

    public static void ErrorAlert(String Content) {
        Alert ds = new Alert(Alert.AlertType.ERROR);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("تمام");
        ds.setHeaderText("خطأ فادح");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Ok);
        Optional<ButtonType> Res = ds.showAndWait();
    }

    public static AlertResult WarningAlert(String Content, EventHandler<ActionEvent> OnOkBut) {
        Alert ds = new Alert(Alert.AlertType.WARNING);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("موافق");
        ButtonType Cancel = new ButtonType("إلغاء");
        ds.setHeaderText("خلي بالك");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == Ok) {
            OnOkBut.handle(new ActionEvent());
        }
        if (Res.get() == Ok) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static AlertResult WarningAlert(String Content) {
        Alert ds = new Alert(Alert.AlertType.WARNING);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("موافق");
        ButtonType Cancel = new ButtonType("إلغاء");
        ds.setHeaderText("خلي بالك");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == Ok) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static void Alert(String Content) {
        Alert ds = new Alert(Alert.AlertType.NONE);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("تمام");
        ds.setHeaderText("رسالة من البرنامج");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Ok);
        Optional<ButtonType> Res = ds.showAndWait();
    }

    public static AlertResult ConfirmationAlert(String Content, EventHandler<ActionEvent> OnOkBut) {
        Alert ds = new Alert(Alert.AlertType.CONFIRMATION);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("نعم");
        ButtonType Cancel = new ButtonType("لا");
        ds.setHeaderText("رسالة تأكيد");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == Ok) {
            OnOkBut.handle(new ActionEvent());
        }
        if (Res.get() == Ok) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static boolean ConfirmationAlert(String Content) {
        Alert ds = new Alert(Alert.AlertType.CONFIRMATION);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        ButtonType Ok = new ButtonType("نعم");
        ButtonType Cancel = new ButtonType("لا");
        ds.setHeaderText("رسالة تأكيد");
        ds.setContentText(Content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();

        return Res.get() == Ok;
    }

    public enum AlertResult {
        Negative, Positive
    }
}
