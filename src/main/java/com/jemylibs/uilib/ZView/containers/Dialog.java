package com.jemylibs.uilib.ZView.containers;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class Dialog extends ZBasedWindow {

    private final HBox buttons_box;

    private SimpleObjectProperty<VBox> dialog_root = new SimpleObjectProperty<>(null);

    public Dialog(String title) {
        super(title);
        buttons_box = new HBox();
        this.buttons_box.setPadding(new Insets(5));
        this.buttons_box.setSpacing(5);
    }

    @Override
    public Parent getView() {
        Parent view = getTheView();
        VBox dialog_root = new VBox(view, this.buttons_box);
        dialog_root.getStyleClass().add("dialog-box");
        dialog_root.setPadding(new Insets(8));
        dialog_root.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(view, Priority.ALWAYS);
        this.dialog_root.set(dialog_root);
        return dialog_root;
    }

    protected abstract Parent getTheView();

    public final void setButtons(int onEnterPressedIndex, Button... buttons) {
        this.buttons_box.getChildren().setAll(buttons);
        if (onEnterPressedIndex != -1) {
            if (dialog_root.get() == null) {
                dialog_root.addListener((observable, oldValue, newValue) -> {
                    newValue.setOnKeyReleased(event -> {
                        if (event.getCode() == KeyCode.ENTER) buttons[onEnterPressedIndex].fire();
                    });
                });
            } else {
                dialog_root.get().setOnKeyReleased(event -> {
                    if (event.getCode() == KeyCode.ENTER) buttons[onEnterPressedIndex].fire();
                });
            }
        }
    }

    public final void setButtons(Button... buttons) {
        this.setButtons(0, buttons);
    }

    public final void setButtons(int onEnterPressedIndex, ObjectTitle<Runnable>... buttons) {
        Button[] buttonss = new Button[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            buttonss[i] = new Button(buttons[i].getTitle());
            int finalI = i;
            buttonss[i].setOnAction(event -> buttons[finalI].get().run());
        }
        setButtons(onEnterPressedIndex, buttonss);
    }

}
