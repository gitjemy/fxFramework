package com.jemylibs.uilib.ctrls.tables.customCols.cells;

import java.util.function.Function;

public class StringCell<T, X> extends Cell<T, X> {
    final private Function<T, String> value;

    public StringCell(Function<T, String> value) {
        this.value = value;
        setWrapText(true);
    }

    @Override
    protected void updateItem(X item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            T currentItem = getCurrentItem();
            String s = value.apply(currentItem);
            if (s != null) {
                setText(s);
            }
        } else {
            setText("");
        }
    }

}
