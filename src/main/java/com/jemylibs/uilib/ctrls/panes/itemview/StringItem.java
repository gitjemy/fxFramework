package com.jemylibs.uilib.ctrls.panes.itemview;

import java.util.function.Function;

import javafx.scene.control.Label;

public class StringItem<T> extends Item<T, Label> {
    final private Function<T, String> value;

    public StringItem(String text, Function<T, String> value) {
        super(text, new Label());
        this.value = value;
        getView().setWrapText(true);
        getView().setMaxHeight(Double.MAX_VALUE);
    }

    @Override
    public void item_update(T t) {
        getView().setText(value.apply(t));
    }
}
