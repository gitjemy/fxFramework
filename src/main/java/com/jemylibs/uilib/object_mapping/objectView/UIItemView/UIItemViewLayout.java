package com.jemylibs.uilib.object_mapping.objectView.UIItemView;

import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.uilib.view.ZNode;
import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public abstract class UIItemViewLayout<E, LayoutType extends Pane> implements ZNode {

    private final LayoutType container;
    private final WritablePropertyControl<E, ?, ?>[] properties;

    public UIItemViewLayout(LayoutType container, WritablePropertyControl<E, ?, ?>... properties) {
        this.container = container;
        this.properties = properties;

        ArrayList<PropertyPaneItem> items = new ArrayList<>();
        items.addAll(Arrays.asList(this.properties));

        items.forEach(d -> {
            addItemToContainer(d, container);
        });
    }

    public abstract void addItemToContainer(PropertyPaneItem item, LayoutType container);

    @Override
    public Parent getView() {
        return container;
    }

    public void validate() throws Exception {
        for (WritablePropertyControl<E, ?, ?> writablePropertyControl : properties) {
            writablePropertyControl.validate();
        }
    }

    public E update(E e) {
        for (WritablePropertyControl<E, ?, ?> writablePropertyControl : properties) {
            writablePropertyControl.write(e);
        }
        return e;
    }

    public void show(E item) {
        for (WritablePropertyControl<E, ?, ?> writablePropertyControl : properties) {
            writablePropertyControl.readAndShow(item);
        }
    }
}
