package com.jemylibs.uilib.object_mapping.propertise.write;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import com.jemylibs.gdb.properties.WritableProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class WritablePropertyChoiceControl<E, V> extends WritablePropertyControl<E, V, ChoiceBox<ObjectTitle<V>>> {


    public <C extends ObjectTitle<V>> WritablePropertyChoiceControl(WritableProperty<E, V> property, ObjectTitle<V>[] choices) {
        super(property, new ChoiceBox<>(FXCollections.observableArrayList(choices)),
                choiceBox -> choiceBox.getSelectionModel().getSelectedItem().get(),
                (choiceBox, d) -> {
                    for (ObjectTitle<V> choice : choices) {
                        if (choice.get().equals(d)) {
                            choiceBox.getSelectionModel().select(choice);
                            break;
                        }
                    }
                });
    }

    public WritablePropertyChoiceControl(WritableProperty<E, V> property, ArrayList<ObjectTitle<V>> choices) {
        super(property, new ChoiceBox<>(FXCollections.observableArrayList(choices)),
                choiceBox -> choiceBox.getSelectionModel().getSelectedItem().get(),
                (choiceBox, d) -> {
                    for (ObjectTitle<V> choice : choices) {
                        if (choice.get().equals(d)) {
                            choiceBox.getSelectionModel().select(choice);
                            break;
                        }
                    }
                });
    }
}
