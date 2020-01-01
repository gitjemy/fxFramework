package com.jemylibs.uilib.ZView.containers;

import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.view.ZNode;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ZBasedWindow implements ZNode {
    private final String title;
    private ArrayList<Consumer<ZBasedWindow>> onClose;
    private Stage stage;

    public ZBasedWindow(String title) {
        this.title = title;
    }

    void initForShow() {
        this.stage = UIController.register_stage(new Stage());
        this.stage.setOnCloseRequest(r -> {
            onClose();
            UIController.unregister_stage(stage);
        });
        this.stage.setTitle(title);
        this.stage.initModality(Modality.WINDOW_MODAL);
        Parent view = getView();
        view.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Scene scene = new Scene(view);
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isShowing() {
        return stage.isShowing();
    }

    public void show() {
        initForShow();
        stage.show();
    }

    private void onClose() {
        if (onClose != null) {
            onClose.forEach(s -> s.accept(this));
        }
    }

    final public void showAndWait() {
        initForShow();
        beforeShow();
        stage.showAndWait();
    }

    protected void beforeShow() {

    }

    public String getTitle() {
        return stage.getTitle();
    }

    public void addOnClose(Consumer<ZBasedWindow> runnable) {
        if (onClose == null) {
            onClose = new ArrayList<>();
        }
        onClose.add(runnable);
    }

    protected void close() {
        UIController.reloadAllViews();
        this.getStage().close();
    }
}
