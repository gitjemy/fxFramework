package com.jemylibs.uilib.ctrls.listviews;

import com.jemylibs.uilib.view.ZNode;

import javafx.scene.control.ListCell;

public class MapperListCell<T extends ZNode> extends ListCell<T> {

    public MapperListCell() {
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(item.getView());
        }
    }
}
