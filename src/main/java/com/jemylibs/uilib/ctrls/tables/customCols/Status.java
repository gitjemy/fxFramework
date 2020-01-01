package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class Status {

    public String text;
    public String color;
    public String textColor;

    public Status(String text, String color, String textColor) {
        this.text = text;
        this.color = color;
        this.textColor = textColor;
    }

    public Status(String text, String color) {
        this.text = text;
        this.color = color;
        this.textColor = "#fff";
    }

    public static Status green(String text) {
        return new Status(text, "#12ff66", "white");
    }

    public static Status blue(String text) {
        return new Status(text, "#1266ff", "white");
    }

    public static Status red(String text) {
        return new Status(text, "#ff1266", "white");
    }


    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Label createLabel() {
        Label label = new Label(text);
        label.setPadding(new Insets(3, 8, 3, 8));
        label.setStyle("-fx-background-color:" + color + " ; " + "-fx-text-fill:" + textColor + "; fx-background-radius:10px");
        return label;
    }
}
