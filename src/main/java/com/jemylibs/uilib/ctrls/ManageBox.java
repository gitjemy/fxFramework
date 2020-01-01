package com.jemylibs.uilib.ctrls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ManageBox extends HBox {
    public Button add, update, remove;

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

    public ManageBox(EventHandler<ActionEvent> AddEvent, EventHandler<ActionEvent> UpdateEvent, EventHandler<ActionEvent> DeleteEvent) {
        this();
        init();
        setActions(AddEvent, UpdateEvent, DeleteEvent);
    }

    private void init() {
        add = new Button("إضافة");
        update = new Button("تعديل");
        remove = new Button("حذف");
        setId("ManageBox");
        setSpacing(5);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(add, update, remove);
    }

    public void setActions(EventHandler<ActionEvent> AddEvent, EventHandler<ActionEvent> UpdateEvent, EventHandler<ActionEvent> DeleteEvent) {
        SetOnAdd(AddEvent);
        SetOnUpdate(UpdateEvent);
        SetOnDelete(DeleteEvent);
    }

    public void SetTexts(String FristBut, String SecondBut, String ThirdBut) {
        add.setText(FristBut);
        update.setText(SecondBut);
        remove.setText(ThirdBut);
    }

    public void SetOnAdd(EventHandler<ActionEvent> e) {
        add.setOnAction(e);
    }

    public void SetOnUpdate(EventHandler<ActionEvent> e) {
        update.setOnAction(e);
    }

    public void SetOnDelete(EventHandler<ActionEvent> e) {
        remove.setOnAction(e);
    }

}
