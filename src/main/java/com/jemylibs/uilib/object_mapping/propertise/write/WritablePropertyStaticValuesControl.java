package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.uilib.ctrls.util;
import javafx.scene.control.ComboBox;

import java.util.List;

public class WritablePropertyStaticValuesControl<E, V> extends WritablePropertyControl<E, V, ComboBox<V>> {

    public WritablePropertyStaticValuesControl(WritableProperty<E, V> property, List<V> choices) {
        super(property, util.createComboBox(choices),
                choiceBox -> choiceBox.getSelectionModel().getSelectedItem(),
                (choiceBox, d) -> {
                    for (V choice : choices) {
                        if (choice.equals(d)) {
                            choiceBox.getSelectionModel().select(choice);
                            break;
                        }
                    }
                });
    }

    public WritablePropertyStaticValuesControl(WritableProperty<E, V> property, V[] choices) {
        super(property, util.createComboBox(choices),
                choiceBox -> choiceBox.getSelectionModel().getSelectedItem(),
                (choiceBox, d) -> {
                    for (V choice : choices) {
                        if (choice.equals(d)) {
                            choiceBox.getSelectionModel().select(choice);
                            break;
                        }
                    }
                });
    }
}
