package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.uilib.ctrls.inputs.NumbersTextField;
import com.jemylibs.gdb.properties.WritableProperty;

public class WritablePropertyNumberControl<E> extends WritablePropertyControl<E, Integer, NumbersTextField> {
    public WritablePropertyNumberControl(WritableProperty<E, Integer> property) {
        super(property, new NumbersTextField(), NumbersTextField::getIntValue, NumbersTextField::setIntValue);
    }
}
