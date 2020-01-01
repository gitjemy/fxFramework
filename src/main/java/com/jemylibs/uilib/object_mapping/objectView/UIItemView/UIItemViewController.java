package com.jemylibs.uilib.object_mapping.objectView.UIItemView;

import com.jemylibs.uilib.ctrls.panes.ItemsPane;
import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;

import java.util.function.Function;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public abstract class UIItemViewController<E> extends UIItemViewLayout<E, ItemsPane> {

    private final Function<Boolean, E> getItem;

    public UIItemViewController(Function<Boolean, E> getItem, WritablePropertyControl<E, ?, ?>... properties) {
        super(new ItemsPane(), properties);
        this.getItem = getItem;
        E apply = getItem.apply(true);
        show(apply);
    }

    @Override
    public Parent getView() {
        Button add = new Button("إضافة");

        Parent view = super.getView();
        VBox vBox = new VBox(view, add);
        vBox.setSpacing(5);

        add.setOnAction(event -> {
            E apply = getItem.apply(true);
            try {
                validate();
                update(apply);
                show(apply);
                onAdd(apply);
                show(getItem.apply(true));
            } catch (Exception e) {
                ZAlert.errorHandle(e);
            }
        });

        vBox.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                add.fire();
            }
        });
        return vBox;
    }

    public abstract void onAdd(E e);

    public void addItemToContainer(PropertyPaneItem item, ItemsPane container) {
        container.getItems().add(item);
    }
}
