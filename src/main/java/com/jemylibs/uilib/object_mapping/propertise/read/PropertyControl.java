package com.jemylibs.uilib.object_mapping.propertise.read;

import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.gdb.properties.Property;
import com.jemylibs.gdb.properties.Writer;
import javafx.scene.Node;

public class PropertyControl<E, V, N extends Node> extends PropertyPaneItem<N> {

    public final Property<E, V> property;
    final private Writer<N, V> controlVal;

    public PropertyControl(Property<E, V> property, N node, Writer<N, V> controlVal) {
        super(property.getTitle(), node);
        this.property = property;
        this.controlVal = controlVal;
    }

    public void readAndShow(E e) {
        V value = property.getValue(e);
        controlVal.set(getNode(), value);
    }
}
