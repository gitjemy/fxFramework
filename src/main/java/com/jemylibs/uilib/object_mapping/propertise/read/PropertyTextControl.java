package com.jemylibs.uilib.object_mapping.propertise.read;

import com.jemylibs.gdb.properties.Property;
import javafx.scene.control.Label;

public class PropertyTextControl<E> extends PropertyControl<E, Object, Label> {

    public PropertyTextControl(Property<E, Object> property) {
        super(property, new Label(), (n, v) -> n.setText(v.toString()));
    }
}
