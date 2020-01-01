package com.jemylibs.uilib.ctrls.panes.itemview;

import java.util.function.Consumer;

import javafx.scene.control.Button;

public class ButtonItem<E> extends Item<E, Button> {
    private E e;
    public ButtonItem(String text, Consumer<E> onClick) {
        super(text, new Button());
        getView().setOnAction(event -> onClick.accept(e));
    }

    @Override
    public void item_update(E e) {
        this.e = e;
    }
}
