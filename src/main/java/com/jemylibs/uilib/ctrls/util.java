package com.jemylibs.uilib.ctrls;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;

public class util {
    public static <E> ComboBox<E> createComboBox(E[] values) {
        return createComboBox(Arrays.asList(values));
    }

    public static <E> ComboBox<E> createComboBox(List<E> values) {
        ComboBox<E> comboBox = new ComboBox<>();
        comboBox.getItems().setAll(values);
        comboBox.getItems().add(null);

        comboBox.setConverter(new StringConverter<E>() {
            String n_a = "بلا خيار";

            @Override
            public String toString(E e) {
                if (e == null) {
                    return n_a;
                } else {
                    return e.toString();
                }
            }

            @Override
            public E fromString(String string) {
                for (E value : values) {
                    if (string.equals(value.toString())) {
                        return value;
                    }
                }
                return null;
            }
        });
        return comboBox;
    }
}
