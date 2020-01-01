package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.function.Function;

public class PropertyCol<E, V> extends col<E, V> {
    public PropertyCol(String s, String propertyName) {
        super(s);
        setCellValueFactory(
                new PropertyValueFactory<>(propertyName));
    }

    public PropertyCol<E, V> setShowText(Function<V, String> function) {
        setCellFactory(column -> new TableCell<E, V>() {
            @Override
            protected void updateItem(V item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(function.apply(item));
                }
            }
        });
        return this;
    }
}
