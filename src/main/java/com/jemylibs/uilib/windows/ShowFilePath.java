package com.jemylibs.uilib.windows;

import com.jemylibs.uilib.ZView.containers.Dialog;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ShowFilePath extends Dialog {

    private final File file;

    public ShowFilePath(String title, File file) {
        super(title);
        this.file = file;
        showAndWait();
    }

    @Override
    protected Parent getTheView() {
        Label label = new Label("مسار الملف : " + file.getAbsolutePath());
        Button openFileButton = new Button("افتح الملف ...");

        Button openDirectoryButton = new Button("افتح الفولدر");

        openDirectoryButton.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(file.getParentFile());
                ShowFilePath.this.close();
            } catch (IOException e1) {
                ZAlert.errorHandle(e1);
            }
        });
        openFileButton.setOnAction(e -> {
            try {
                Desktop.getDesktop().open(file);
                ShowFilePath.this.close();
            } catch (IOException e1) {
                ZAlert.errorHandle(e1);
            }
        });
        VBox vBox = new VBox(label, openFileButton, openDirectoryButton);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        return vBox;
    }
}
