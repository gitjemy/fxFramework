package com.jemylibs.uilib.ctrls.tables.customCols.cells;

import com.jemylibs.uilib.ctrls.tables.customCols.Status;

import java.util.function.Function;

public class StatusCell<Item> extends Cell<Item, Integer> {
    private Function<Item, Status> statusFunction;

    public StatusCell(Function<Item, Status> statusFunction) {
        this.statusFunction = statusFunction;
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            Item currentItem = getCurrentItem();
            Status status = statusFunction.apply(currentItem);
            if (!status.text.isEmpty()) {
                setGraphic(status.createLabel());
            } else {
                setText("");
                setGraphic(null);
            }
        } else {
            setText("");
            setGraphic(null);
        }
    }

}
