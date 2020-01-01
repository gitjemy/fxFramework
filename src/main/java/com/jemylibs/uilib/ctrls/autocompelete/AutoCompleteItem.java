package com.jemylibs.uilib.ctrls.autocompelete;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AutoCompleteItem<T> extends ObjectTitle<T> {

    private final BooleanProperty hidden = new SimpleBooleanProperty();

    public AutoCompleteItem(String title, T t) {
        super(title, t);
    }

    public BooleanProperty hiddenProperty() {
        return this.hidden;
    }

    public boolean isHidden() {
        return this.hiddenProperty().get();
    }

    public void setHidden(boolean hidden) {
        this.hiddenProperty().set(hidden);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
