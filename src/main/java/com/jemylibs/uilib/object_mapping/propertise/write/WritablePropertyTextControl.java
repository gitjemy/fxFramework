package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.gdb.properties.WritableProperty;
import javafx.scene.control.TextField;

public class WritablePropertyTextControl<E> extends WritablePropertyControl<E, String, TextField> {
    public WritablePropertyTextControl(WritableProperty<E, String> property) {
        super(property, new TextField(), textField -> {
            String text = textField.getText();
            return text == null ? "" : text;
        }, TextField::setText);
    }
}
