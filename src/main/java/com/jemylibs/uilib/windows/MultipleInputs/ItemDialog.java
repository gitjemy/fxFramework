package com.jemylibs.uilib.windows.MultipleInputs;

import com.jemylibs.uilib.ZView.containers.ZResultDialog;
import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;
import com.jemylibs.uilib.object_mapping.objectView.UIItemView.UIItemView;

import java.util.List;

import javafx.scene.Parent;

public abstract class ItemDialog<E> extends ZResultDialog<E> {
    private E item;
    private UIItemView<E> euiItemView;

    public ItemDialog(String title, E item, List<WritablePropertyControl<E, ?, ?>> items) {
        super(title);
        this.item = item;
        euiItemView = new UIItemView<>(true, items);
        if (item != null) {
            euiItemView.show(item);
        } else {
            euiItemView.show(item);
        }
    }

    @Override
    protected Parent getTheView() {
        return euiItemView.getView();
    }

    @Override
    public void validation() throws Throwable {
        euiItemView.validate();
    }

    @Override
    protected E getResult() throws Throwable {
        if (item == null) {
            return item;
        } else {
            return euiItemView.update(item);
        }
    }
}
