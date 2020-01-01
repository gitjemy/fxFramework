package com.jemylibs.uilib.object_mapping.objectView.UIItemView;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import com.jemylibs.uilib.ctrls.panes.ItemsPane;
import com.jemylibs.uilib.ctrls.panes.PropertyPane;
import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;
import com.jemylibs.uilib.utilities.ZValidate;
import com.jemylibs.uilib.view.ZNode;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UIItemView<E> implements ZNode {

    public final List<WritablePropertyControl<E, ?, ?>> properties;
    private StackPane pane = new StackPane();

    public UIItemView(boolean vertical, ObjectTitle<Consumer<UIItemView<E>>>[] buttons, List<WritablePropertyControl<E, ?, ?>> properties) {
        this.properties = properties;
        ArrayList<WritablePropertyControl<E, ?, ?>> items = new ArrayList<>(this.properties);

        Parent mContainer;
        if (vertical) {
            PropertyPane propertyPane = new PropertyPane();
            propertyPane.setItems(items.toArray(new PropertyPaneItem[0]));
            mContainer = propertyPane;
        } else {
            ItemsPane itemsPane = new ItemsPane();
            mContainer = itemsPane;
            itemsPane.setItems(items.toArray(new PropertyPaneItem[0]));
        }

        if (buttons != null) {
            HBox buttonsBox = new HBox();
            buttonsBox.setSpacing(10);
            buttonsBox.setPadding(new Insets(5));
            for (ObjectTitle<Consumer<UIItemView<E>>> button : buttons) {
                Button button1 = new Button(button.getTitle());
                button1.setOnAction(event -> button.get().accept(this));
                buttonsBox.getChildren().add(button1);
            }
            pane.getChildren().setAll(new VBox(mContainer, buttonsBox));
        } else {
            pane.getChildren().setAll(mContainer);
        }
    }

    public UIItemView(boolean vertical, List<WritablePropertyControl<E, ?, ?>> properties) {
        this(vertical, null, properties);
    }

    public WritablePropertyControl<E, ?, ?> getById(String id) {
        for (WritablePropertyControl<E, ?, ?> property : properties) {
            String id1 = property.getNode().getId();
            if (id.equals(id1)) return property;
        }
        return null;
    }

    @Override
    public Parent getView() {
        return pane;
    }

    public void validate() throws Exception {
        for (WritablePropertyControl<E, ?, ?> writablePropertyControl : properties) {
            try {
                writablePropertyControl.validate();
            } catch (Throwable e) {
                throw new ZValidate(writablePropertyControl.getNode(), e);
            }
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
