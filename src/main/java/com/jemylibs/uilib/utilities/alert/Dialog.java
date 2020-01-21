package com.jemylibs.uilib.utilities.alert;

import com.jemylibs.uilib.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.function.Consumer;

public class Dialog {

    public static void ErrorAlert(String title, String content) {
        Alert ds = new Alert(Alert.AlertType.ERROR);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType Ok = new ButtonType(Application.getApplication().getBundle().getString("Button_Ok"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(Ok);
        Optional<ButtonType> Res = ds.showAndWait();
    }

    public static AlertResult WarningAlert(String title, String content, EventHandler<ActionEvent> onOkBut) {
        Alert ds = new Alert(Alert.AlertType.WARNING);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType Ok = new ButtonType(Application.getApplication().getBundle().getString("Button_Ok"));
        ButtonType Cancel = new ButtonType(Application.getApplication().getBundle().getString("Button_Cancel"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == Ok) {
            onOkBut.handle(new ActionEvent());
        }
        if (Res.get() == Ok) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static AlertResult WarningAlert(String title, String content) {
        Alert ds = new Alert(Alert.AlertType.WARNING);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType Ok = new ButtonType(Application.getApplication().getBundle().getString("Button_Ok"));
        ButtonType Cancel = new ButtonType(Application.getApplication().getBundle().getString("Button_Cancel"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(Cancel, Ok);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == Ok) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static void Alert(String title, String content) {
        Alert ds = new Alert(Alert.AlertType.NONE);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType Ok = new ButtonType(Application.getApplication().getBundle().getString("Button_Ok"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(Ok);
        Optional<ButtonType> Res = ds.showAndWait();
    }

    public static AlertResult ConfirmationAlert(String title, String content, EventHandler<ActionEvent> onOkBut) {
        Alert ds = new Alert(Alert.AlertType.CONFIRMATION);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType button_yes = new ButtonType(Application.getApplication().getBundle().getString("Button_Yes"));
        ButtonType button_no = new ButtonType(Application.getApplication().getBundle().getString("Button_No"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(button_no, button_yes);
        Optional<ButtonType> Res = ds.showAndWait();
        if (Res.get() == button_yes) {
            onOkBut.handle(new ActionEvent());
        }
        if (Res.get() == button_yes) {
            return AlertResult.Positive;
        } else {
            return AlertResult.Negative;
        }
    }

    public static boolean ConfirmationAlert(String title, String content) {
        Alert ds = new Alert(Alert.AlertType.CONFIRMATION);
        ds.getDialogPane().getStyleClass().add("ZAlert");
        ds.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        ButtonType button_yes = new ButtonType(Application.getApplication().getBundle().getString("Button_Yes"));
        ButtonType button_no = new ButtonType(Application.getApplication().getBundle().getString("Button_No"));
        ds.setHeaderText(title);
        ds.setContentText(content + ".");
        ds.getButtonTypes().setAll(button_no, button_yes);
        Optional<ButtonType> Res = ds.showAndWait();
        return (Res.isPresent()) && Res.get() == button_yes;
    }

    public static void input(String title, String headerText, String textBoxText, Consumer<String> consumer) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(textBoxText);
        dialog.getDialogPane().setNodeOrientation((NodeOrientation) Application.getApplication().getBundle().getObject("Orientation"));
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(consumer);
    }

    public enum AlertResult {
        Negative, Positive
    }
}
