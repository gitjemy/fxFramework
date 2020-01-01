package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.uilib.ctrls.inputs.DecimalTextField;
import com.jemylibs.gdb.properties.WritableProperty;

import java.math.BigDecimal;

public class WritablePropertyDecimalControl<E> extends WritablePropertyControl<E, BigDecimal, DecimalTextField> {

    public WritablePropertyDecimalControl(WritableProperty<E, BigDecimal> property) {
        super(property, new DecimalTextField(), DecimalTextField::getValue, DecimalTextField::setValue);
    }

}
