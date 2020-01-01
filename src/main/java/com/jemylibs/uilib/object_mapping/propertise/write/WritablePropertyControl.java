package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.gdb.properties.Writer;
import javafx.scene.Node;

import java.util.function.Function;


public class WritablePropertyControl<E, V, N extends Node> extends PropertyPaneItem<N> {

    public final WritableProperty<E, V> property;

    final private Function<N, V> get_val_from_control;
    final private Writer<N, V> controlVal;

    public WritablePropertyControl(WritableProperty<E, V> property, N node,
                                   Function<N, V> get_val_from_control,
                                   Writer<N, V> controlVal) {
        super(property.getTitle(), node);
        this.property = property;
        this.get_val_from_control = get_val_from_control;
        this.controlVal = controlVal;
    }

    public Function<N, V> getGet_val_from_control() {
        return get_val_from_control;
    }

    public String getId() {
        return getNode().getId();
    }

    public void setId(String id) {
        getNode().setId(id);
    }


    public Writer<N, V> getControlVal() {
        return controlVal;
    }

    public void readAndShow(E e) {
        V value = property.getValue(e);
        controlVal.set(getNode(), value);
    }

    public void validate() throws Exception {
        V v = get_val_from_control.apply(getNode());
        property.validate(v);
    }

    public void write(E e) {
        V v = get_val_from_control.apply(getNode());
        property.setValue(e, v);
    }

    public WritableProperty<E, V> getProperty() {
        return property;
    }
}
