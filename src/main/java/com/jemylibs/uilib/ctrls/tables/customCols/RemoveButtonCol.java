package com.jemylibs.uilib.ctrls.tables.customCols;

import java.util.function.Consumer;

public class RemoveButtonCol<E> extends ButtonCol<E> {

    public RemoveButtonCol() {
        super("حذف", "x", "remove_button", null);
    }

    public RemoveButtonCol(Consumer<E> onClick) {
        super("حذف", "x", "remove_button", onClick);
    }

    public void doAction(E item) {
        getTableView().getItems().remove(item);
        super.doAction(item);
    }
}
