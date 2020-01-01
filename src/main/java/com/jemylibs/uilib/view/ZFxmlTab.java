package com.jemylibs.uilib.view;

import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.ZView.containers.ZTb;

import javafx.scene.Parent;

public abstract class ZFxmlTab extends ZTb implements ZFxml {
    private Parent parent;

    @Override
    public Parent getView() {
        return parent;
    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
    }
}
