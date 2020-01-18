package com.jemylibs.uilib.ctrls;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.List;

public class ManageBox extends HBox {

    public ManageBox() {
        super();
        init();
    }

    public ManageBox(double spacing) {
        super(spacing);
        init();
    }

    public ManageBox(Node... children) {
        super(children);
        init();
    }

    public ManageBox(double spacing, Node... children) {
        super(spacing, children);
        init();
    }

    private void init() {
        setId("ManageBox");
        setSpacing(5);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER_LEFT);
    }


    public void setActions(ObjectTitle<EventHandler<ActionEvent>>... buttons) {
        getChildren().clear();
        for (ObjectTitle<EventHandler<ActionEvent>> button : buttons) {
            Button butt = new Button(button.getTitle());
            getChildren().add(butt);
            butt.setOnAction(button.get());
        }
    }

    public void setActions(List<Button> buttons) {
        getChildren().setAll(buttons);
    }
}
