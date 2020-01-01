package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.gdb.properties.WritableProperty;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class WritablePropertyDateControl<E> extends WritablePropertyControl<E, LocalDate, DatePicker> {
    public WritablePropertyDateControl(WritableProperty<E, LocalDate> property) {
        super(property, new DatePicker(), DatePicker::getValue, DatePicker::setValue);
    }

}
