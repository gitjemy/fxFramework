package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.uilib.ctrls.ZItemChoiceBox;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.properties.WritableProperty;

public class WritablePropertyEntityChoiceControl<E, T extends ZSqlRow> extends WritablePropertyControl<E, Integer, ZItemChoiceBox<T>> {

    public WritablePropertyEntityChoiceControl(WritableProperty<E, Integer> property) {
        super(property, new ZItemChoiceBox<>(), ZItemChoiceBox::getSelectedId, ZItemChoiceBox::setSelectedId);
    }
}
