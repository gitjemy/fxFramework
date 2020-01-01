package com.jemylibs.uilib.ctrls.panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PropertyPaneItem<E extends Node> {

    final private String Title;
    final private E node;

    public PropertyPaneItem(String Title, E Node) {
        this.Title = Title;
        this.node = Node;
    }

    public PropertyPaneItem(E Node) {
        this.Title = "";
        this.node = Node;
    }

    public PropertyPaneItem(String Title, String text) {
        this.Title = Title;
        this.node = (E) new Label(text);
    }

    public PropertyPaneItem(String button_text, EventHandler<ActionEvent> onclick) {
        this.Title = "";
        Button button = new Button(button_text);
        button.setOnAction(onclick);
        this.node = (E) button;
    }


    public E getNode() {
        return node;
    }

    public String getTitle() {
        return Title;
    }

    public PropertyPane toPane() {
        PropertyPane propertyPane = new PropertyPane();
        propertyPane.setItems(this);
        return propertyPane;
    }
}
