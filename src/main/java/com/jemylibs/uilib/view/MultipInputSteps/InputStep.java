package com.jemylibs.uilib.view.MultipInputSteps;

import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.ctrls.panes.PropertyPane;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public abstract class InputStep extends VBox {

    public final PropertyPane PropertyPane;
    public final StackPane TopPane;
    String Title;

    public InputStep(String Title) {
        this.Title = Title;
        PropertyPane = new PropertyPane();
        TopPane = new StackPane();
        setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setSpacing(10.0);
        setPadding(new Insets(10.0));
        getChildren().add(TopPane);
        getChildren().add(PropertyPane);
        init();
    }

    abstract public void Validation() throws Exception;

    abstract public void OnValidate() throws Exception;

    public void init() {
    }
}
