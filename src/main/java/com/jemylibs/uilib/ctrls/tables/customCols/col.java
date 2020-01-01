package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.TableColumn;

abstract public class col<T, X> extends TableColumn<T, X> {
    public col(String s) {
        super(s);
    }

    public col<T, X> setHidden() {
        setVisible(false);
        return this;
    }
}
