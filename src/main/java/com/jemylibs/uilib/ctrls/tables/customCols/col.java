package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.util.function.Function;

abstract public class col<T, X> extends TableColumn<T, X> {
    public col(String s) {
        super(s);
    }

    public col<T, X> setHidden() {
        setVisible(false);
        return this;
    }

    public col<T, X> setTitle(String value) {
        setText(value);
        return this;
    }

    public col<T, X> setShowText(Function<X, String> function) {
        setCellFactory(column -> new TableCell<T, X>() {
            @Override
            protected void updateItem(X item, boolean empty) {
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
