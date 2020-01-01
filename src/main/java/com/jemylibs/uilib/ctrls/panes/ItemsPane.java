package com.jemylibs.uilib.ctrls.panes;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;

public class ItemsPane extends HBox {

    private ObservableList<PropertyPaneItem> items = FXCollections.observableArrayList();

    public ItemsPane() {
        super();
        Refresh();
        this.items.addListener((ListChangeListener.Change<? extends PropertyPaneItem> c) -> {
            Refresh();
        });
    }

    public void Refresh() {
        clear();
        PropertyPane flowPane = new PropertyPane();
        getChildren().add(flowPane);
        flowPane.getItems().setAll(items);
    }

    public void clear() {
        getChildren().clear();
    }

    public ObservableList<PropertyPaneItem> getItems() {
        return items;
    }

    public void setItems(List<PropertyPaneItem> Items) {
        this.items.setAll(Items);
        Refresh();
    }

    public void setItems(PropertyPaneItem... Items) {
        this.items.setAll(Items);
        Refresh();
    }

    public void add(PropertyPaneItem... Items) {
        this.items.addAll(Items);
        Refresh();
    }
}
