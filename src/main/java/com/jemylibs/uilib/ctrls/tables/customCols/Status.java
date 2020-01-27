package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.Label;

public class Status {

    final private String text;
    final private String color;
    final private String styleClass;

    public Status(String text, String color, String styleClass) {
        this.text = text;
        this.color = color;
        this.styleClass = styleClass;
    }

    public static Status green(String text) {
        return new Status(text, "#12ff66", null);
    }

    public static Status blue(String text) {
        return new Status(text, "#1266ff", null);
    }

    public static Status red(String text) {
        return new Status(text, "#ff1266", null);
    }


    public Label createLabel() {
        Label label = new Label(text);
        label.setStyle("-fx-base : " + color + ";");
        if (styleClass != null) label.getStyleClass().add(styleClass);
        label.getStyleClass().add("status-col-cell");
        return label;
    }
}
