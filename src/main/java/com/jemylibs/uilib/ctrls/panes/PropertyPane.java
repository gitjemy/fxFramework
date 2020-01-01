package com.jemylibs.uilib.ctrls.panes;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Arrays;
import java.util.List;

public class PropertyPane extends GridPane {

    private ObservableList<PropertyPaneItem> Items = FXCollections.observableArrayList();

    {
        setVgap(5);
        setHgap(5);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER);
        getStyleClass().add("z-item-view");
        this.Items.addListener((ListChangeListener.Change<? extends PropertyPaneItem> c) -> {
            Refresh();
        });
    }

    public PropertyPane() {
        super();
    }

    private void Refresh() {
        clear();
        for (int i = 0; i < Items.size(); i++) {
            PropertyPaneItem get = Items.get(i);
            Node node = get.getNode();

            if (!get.getTitle().equals("")) {
                Label label = new Label(get.getTitle());
                label.setAlignment(Pos.TOP_LEFT);
                label.setMaxWidth(Double.MAX_VALUE);
                setValignment(label, VPos.CENTER);
                label.getStyleClass().add("item-label");
                label.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
                add(label, 0, i);
                add(node, 1, i);
            } else {
                node.getStyleClass().add("item-node-alone");
                add(node, 0, i, 2, 1);
            }

            setHgrow(node, Priority.ALWAYS);

            if (node instanceof Pane) {
//                getRowConstraints().get(i).setVgrow(Priority.ALWAYS);
                setVgrow(node, Priority.ALWAYS);
            }

            setHgap(5);
            setVgap(8);
            node.getStyleClass().add("item-node");

            if (node instanceof Control) {
                ((Control) node).setMaxWidth(Double.MAX_VALUE);
            }

        }
    }

    private void clear() {
        getChildren().clear();
        getRowConstraints().clear();
        getColumnConstraints().clear();
    }

    public void RemoveRow(int ZeroBaseIndex) {
        Items.remove(ZeroBaseIndex);
        Refresh();
    }

    public ObservableList<PropertyPaneItem> getItems() {
        return Items;
    }

    public void setItems(List<? extends PropertyPaneItem> Items) {
        this.Items.setAll(Items);
        Refresh();
    }

    public void setItems(PropertyPaneItem... Items) {
        setItems(Arrays.asList(Items));
    }

    public FlowPane toVertical() {
        FlowPane flowPane = new FlowPane();
        ObservableList<PropertyPaneItem> items = getItems();
        for (PropertyPaneItem item : items) {
            PropertyPane pane = new PropertyPane();
            pane.setItems(item);
            flowPane.getChildren().add(pane);
        }
        return flowPane;
    }

}
