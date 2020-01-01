package com.jemylibs.uilib.view;

import com.jemylibs.uilib.ZView.ZFxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public abstract class ZfxmlListItem<T> extends ListCell<T> implements ZFxml {

    Parent root;

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            fillData(item);
            setGraphic(root);
        }
    }

    abstract protected void fillData(T item);

    @Override
    public void setView(Parent parent) {
        this.root = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
