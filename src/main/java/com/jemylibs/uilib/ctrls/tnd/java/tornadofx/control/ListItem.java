package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control;

import com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control.skin.ListItemSkin;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class ListItem extends Control {
    private final StringProperty textProperty = new SimpleStringProperty(this, "text");
    private Node graphic;

    public ListItem() {
        getStyleClass().add("list-item");
        setFocusTraversable(true);
    }

    public ListItem(String text) {
        this();
        setText(text);
    }

    public ListItem(String text, Node graphic) {
        this(text);
        setGraphic(graphic);
    }

    public Node getGraphic() {
        return graphic;
    }

    public void setGraphic(Node graphic) {
        this.graphic = graphic;
    }

    public String getText() {
        return textProperty.get();
    }

    public void setText(String text) {
        textProperty.set(text);
    }

    public StringProperty getTextProperty() {
        return textProperty;
    }

    protected Skin<?> createDefaultSkin() {
        return new ListItemSkin(this);
    }

    public void needsLayout() {
        setNeedsLayout(true);
        requestLayout();
    }

    public boolean getActive() {
        return getMenu().getActive() == this;
    }

    public void setActive(boolean active) {
        Platform.runLater(() -> {
            ListMenu menu = getMenu();

            if (active) {
                menu.setActive(ListItem.this);
            } else {
                if (menu.getActive() == ListItem.this)
                    menu.setActive(null);
            }
        });
    }

    private ListMenu getMenu() {
        return (ListMenu) getParent();
    }
}
