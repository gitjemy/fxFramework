package com.jemylibs.uilib.ctrls.panes.itemview;

import javafx.scene.Node;

public abstract class Item<I, N extends Node> {
    final private String text;
    final private N n;

    public Item(String text, N n) {
        this.text = text;
        this.n = n;
    }

    public String getText() {
        return text;
    }

    public N getView() {
        return n;
    }

    public abstract void item_update(I i);
}
