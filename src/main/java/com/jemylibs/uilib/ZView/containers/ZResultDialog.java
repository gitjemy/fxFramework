package com.jemylibs.uilib.ZView.containers;

import com.jemylibs.uilib.utilities.alert.ZAlert;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class ZResultDialog<Result> extends Dialog {

    private final Label error_msg_label = new Label();
    private Result result = null;

    public ZResultDialog(String title) {
        super(title);
        Button ok = new Button("موافق");
        ok.setOnAction(this::onOk);
        Button cancel = new Button("الغاء");
        cancel.setOnAction(this::onCancel);
        setButtons(ok, cancel);
    }

    protected void validation() throws Throwable {

    }

    protected abstract Result getResult() throws Throwable;

    protected abstract Parent getTheView();

    public void onOk(ActionEvent event) {
        try {
            validation();
            result = getResult();
            onValidated(result);
            close();
        } catch (Throwable e) {
            ZAlert.errorHandle(e, error_msg_label);
        }
    }

    protected abstract void onValidated(Result result) throws Throwable;

    public void onCancel(ActionEvent event) {
        result = null;
        close();
        onNull();
    }

    private void onNull() {

    }
}
