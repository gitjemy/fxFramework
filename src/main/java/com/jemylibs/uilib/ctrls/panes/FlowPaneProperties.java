package com.jemylibs.uilib.ctrls.panes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class FlowPaneProperties extends FlowPane {

    ObservableList<PropertyPaneItem> items = FXCollections.observableArrayList();

    public FlowPaneProperties() {
        super();
        setVgap(8);
        setHgap(8);
        setPadding(new Insets(10));
        refresh();
        this.items.addListener((ListChangeListener.Change<? extends PropertyPaneItem> c) -> refresh());
    }

    protected Node createItemToTheLayout(PropertyPaneItem get) {
        Node node = get.getNode();
        Label label = new Label(get.getTitle());
        label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        HBox box = new HBox(label, node);
        box.setAlignment(Pos.CENTER);
        if (get.getTitle().isEmpty()) {
            box.getStyleClass().add("flow-property-pane-item");
        }
        box.setSpacing(10);
        box.setPadding(new Insets(3, 4, 3, 10));
        return box;
    }

    public void refresh() {
        clear();
        ArrayList<PropertyPaneItem> large = new ArrayList<>();
        ArrayList<PropertyPaneItem> small = new ArrayList<>();

        for (PropertyPaneItem propertyPaneItem : items) {
            Node node = propertyPaneItem.getNode();
            if (node instanceof Pane) {
                large.add(propertyPaneItem);
            } else {
                small.add(propertyPaneItem);
            }
        }

        getChildren().setAll(large.stream().map(this::createItemToTheLayout).collect(Collectors.toList()));
        PropertyPane flowPane = new PropertyPane();
        getChildren().add(flowPane);
        flowPane.getItems().setAll(small);
        flowPane.setVgap(5);
        flowPane.setHgap(5);
    }

    public void clear() {
        getChildren().clear();
    }

    public void clearItems() {
        getItems().clear();
        refresh();
    }

    public void RemoveRow(int ZeroBaseIndex) {
        items.remove(ZeroBaseIndex);
        refresh();
    }

    public ObservableList<PropertyPaneItem> getItems() {
        return items;
    }

    public void setItems(List<PropertyPaneItem> Items) {
        this.items.setAll(Items);
        refresh();
    }

    public void setItems(PropertyPaneItem... Items) {
        this.items.setAll(Items);
        refresh();
    }

    public void add(PropertyPaneItem... Items) {
        this.items.addAll(Items);
        refresh();
    }
}
