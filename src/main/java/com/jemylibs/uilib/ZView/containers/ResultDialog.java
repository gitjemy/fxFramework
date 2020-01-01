package com.jemylibs.uilib.ZView.containers;

import com.jemylibs.uilib.object_mapping.propertise.write.WritablePropertyControl;
import com.jemylibs.uilib.object_mapping.objectView.UIItemView.UIItemView;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Parent;

public abstract class ResultDialog<Result> extends ZResultDialog<Result> {

    private final Result item;
    private final List<WritablePropertyControl<Result, ?, ?>> controls;
    UIItemView<Result> itemView;

    public ResultDialog(String title, Result item, List<WritablePropertyControl<Result, ?, ?>> controls) {
        super(title);
        this.item = item;
        this.controls = controls;
        itemView = new UIItemView<>(true, this.controls);
        itemView.show(this.item);
    }

    public ResultDialog(String title, Result item, WritablePropertyControl<Result, ?, ?>... controls) {
        this(title, item, Arrays.asList(controls));
    }

    @Override
    protected Result getResult() throws Throwable {
        return itemView.update(this.item);
    }

    @Override
    protected Parent getTheView() {
        return this.itemView.getView();
    }

    @Override
    protected void validation() throws Throwable {
        super.validation();
        itemView.validate();
    }
}
