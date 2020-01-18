package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import javafx.scene.layout.Region;

public abstract class FILTER<N extends Region> extends PropertyPaneItem<N> {

    public FILTER(String Title, N Node) {
        super(Title, Node);
    }

    abstract public Condition getCondition();

    abstract public Condition getCondition(String v);

    public abstract void clearValue();


    abstract void setOnChange(Runnable runnable);
}
