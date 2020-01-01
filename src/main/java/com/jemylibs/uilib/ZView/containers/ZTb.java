package com.jemylibs.uilib.ZView.containers;

import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.view.ZBaseSystemView;

import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

public abstract class ZTb extends Tab implements ZBaseSystemView {

    public ZTb() {
        this.setText(this.getTitle());
        setOnCloseRequest(event -> close());
    }

    public static <E extends ZTb> E open(String res_path) {
        E ztab = ZFxml.get(res_path);
        ztab.open();
        return ztab;
    }

    public boolean canClose() {
        return true;
    }

    abstract public String getTitle();

    final public <ZTB extends ZTb> ZTB open() {
        if (isOpenedAsTab()) {
            UIController.mainView.MainStageTabPane.getSelectionModel().select(this);
            this.reload(source.external);
        } else {
            if (canOpen()) {
                setContent(getView());
                UIController.mainView.MainStageTabPane.getTabs().remove(this);
                UIController.mainView.MainStageTabPane.getTabs().add(this);
                UIController.mainView.MainStageTabPane.getSelectionModel().select(this);
                reload(source.external);
            }
        }
        return (ZTB) this;
    }

    public boolean canOpen() {
        return true;
    }


    public boolean isOpenedAsTab() {
        boolean isOpened = false;
        ObservableList<Tab> Tabs = UIController.mainView.MainStageTabPane.getTabs();
        for (Tab Tab1 : Tabs) {
            if (Tab1 == this) {
                isOpened = true;
                break;
            }
        }
        return isOpened;
    }

    protected void close() {
        if (canClose()) {
            UIController.mainView.MainStageTabPane.getTabs().remove(this);
            setContent(null);
            System.gc();
        }
    }
}
