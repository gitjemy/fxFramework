package com.jemylibs.uilib.object_mapping.ObjectMapper;

import com.jemylibs.uilib.object_mapping.objectView.UIItemView.UIItemView;
import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;
import com.jemylibs.gdb.properties.WritableProperty;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;


public class UIObjectView<E> extends UIItemView<E> {

    public UIObjectView(boolean vertical, FieldMapper<E, ?>... properties) {
        super(vertical, create(properties));
    }

    static private <E> ArrayList<WritablePropertyControl<E, ?, ?>> create(ArrayList<FieldMapper<E, ?>> properties) {

        ArrayList<WritablePropertyControl<E, ?, ?>> list = new ArrayList<>();
        for (FieldMapper<E, ?> property : properties) {
            list.addAll(create(property));
        }

        return list;
    }

    static private <E> List<WritablePropertyControl<E, ?, ?>> create(FieldMapper<E, ?>... properties) {
        ArrayList<WritablePropertyControl<E, ?, ?>> list = new ArrayList<>();
        for (FieldMapper<E, ?> property : properties) {
            list.addAll(create(property));
        }
        return list;
    }

    static private <P, C> ArrayList<WritablePropertyControl<P, ?, ?>> create(FieldMapper<P, C> fieldMappers) {
        ArrayList<WritablePropertyControl<P, ?, ?>> list = new ArrayList<>();
        for (WritablePropertyControl<C, ?, ?> control : fieldMappers.controls) {
            list.add(get(fieldMappers, control));
        }
        return list;
    }


    private static <P, C, V, N extends Node> WritablePropertyControl<P, V, N> get(FieldMapper<P, C> etFieldMapper, WritablePropertyControl<C, V, N> control) {
        return new WritablePropertyControl<P, V, N>(
                new WritableProperty<>(control.getTitle(),
                        e -> control.property.getReader().apply(etFieldMapper.apply(e)),
                        (e, v) -> control.property.setValue(etFieldMapper.apply(e), v))
                , control.getNode(), control.getGet_val_from_control(),
                control.getControlVal()
        ) {
            @Override
            public void validate() throws Exception {
                control.validate();
                super.validate();
            }
        };
    }
}
