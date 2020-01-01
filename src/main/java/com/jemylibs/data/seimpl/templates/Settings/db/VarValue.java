package com.jemylibs.data.seimpl.templates.Settings.db;

import com.jemylibs.data.seimpl.helpers.ZItem;

public class VarValue<T> extends ZItem {
    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
