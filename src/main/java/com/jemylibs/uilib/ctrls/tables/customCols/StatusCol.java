package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.ctrls.tables.customCols.cells.StatusCell;

import java.util.function.Function;

public class StatusCol<Item, V> extends PropertyCol<Item, V> {

    public StatusCol(String title, String propertyName, Function<Item, Status> statusFunction) {
        super(title, propertyName);
        setCellFactory(column -> new StatusCell<>(statusFunction));
    }
}
