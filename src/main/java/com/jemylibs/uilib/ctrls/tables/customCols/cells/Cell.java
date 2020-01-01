package com.jemylibs.uilib.ctrls.tables.customCols.cells;

import javafx.scene.control.TableCell;

abstract public class Cell<I, V> extends TableCell<I, V> {
    public I getCurrentItem() {
        return getTableView().getItems().get(getIndex());
    }
}
