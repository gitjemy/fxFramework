package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.uilib.ctrls.inputs.PercentTextField;
import com.jemylibs.gdb.properties.WritableProperty;

import java.math.BigDecimal;

public class WritablePropertyPercentControl<E> extends WritablePropertyControl<E, BigDecimal, PercentTextField> {

    public WritablePropertyPercentControl(WritableProperty<E, BigDecimal> property) {
        super(property, new PercentTextField(), PercentTextField::getValue, PercentTextField::setValue);
    }

}
