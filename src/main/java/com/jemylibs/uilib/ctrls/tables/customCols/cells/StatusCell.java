package com.jemylibs.uilib.ctrls.tables.customCols.cells;

import com.jemylibs.uilib.ctrls.tables.customCols.Status;

import java.util.function.Function;

public class StatusCell<Item, V> extends Cell<Item, V> {
    private Function<Item, Status> statusFunction;

    public StatusCell(Function<Item, Status> statusFunction) {
        this.statusFunction = statusFunction;
        setWrapText(true);
    }

    @Override
    protected void updateItem(V item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            Item currentItem = getCurrentItem();
            Status status = statusFunction.apply(currentItem);
            setGraphic(status.createLabel());
        } else {
            setText("");
            setGraphic(null);
        }
    }

}
