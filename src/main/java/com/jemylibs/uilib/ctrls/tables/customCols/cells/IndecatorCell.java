package com.jemylibs.uilib.ctrls.tables.customCols.cells;

import com.jemylibs.uilib.ctrls.tables.customCols.IndicatorCol;

import javafx.scene.control.Label;

public class IndecatorCell<Item> extends Cell<Item, Integer> {
    private final IndicatorCol.applier<Item> val;
    private final IndicatorCol.applier<Item> min;
    private final IndicatorCol.applier<Item> max;

    public IndecatorCell(IndicatorCol.applier<Item> val,
                         IndicatorCol.applier<Item> min,
                         IndicatorCol.applier<Item> max) {
        this.val = val;
        this.min = min;
        this.max = max;
    }


    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            Item currentItem = getCurrentItem();
            int v = val.get_progress(currentItem);
            int mn = min.get_progress(currentItem);
            int mx = max.get_progress(currentItem);

            Label label = new Label(v + "/" + mx);
            label.getStyleClass().setAll("indicator-cell");
            if (v < mn) {
                label.getStyleClass().add("indicator-low");
            } else if (v > mx) {
                label.getStyleClass().add("indicator-high");
            } else {
                label.getStyleClass().add("indicator-fine");
            }
            setGraphic(label);
        } else {
            setText("");
            setGraphic(null);
        }
    }
}
