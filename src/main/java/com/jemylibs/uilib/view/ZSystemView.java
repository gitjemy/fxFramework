package com.jemylibs.uilib.view;

import com.jemylibs.uilib.ZView.containers.ZBasedWindow;
import com.jemylibs.uilib.ZView.containers.ZTb;
import javafx.scene.Parent;

import java.util.ArrayList;

public abstract class ZSystemView implements ZBaseSystemView {

    public static ArrayList<ZBaseSystemView> openedDialogs = new ArrayList<>();
    private ZBasedWindow mZBasedWindow = null;

    protected boolean canOpen() {
        return true; // todo make this method abstract
    }

    protected boolean canClose() {
        return true; // todo make this method abstract
    }

    public abstract String getTitle();

    public void openAsDialog() {
        if (mZBasedWindow == null) {
            mZBasedWindow = new ZBasedWindow(getTitle()) {
                @Override
                public Parent getView() {
                    return ZSystemView.this.getView();
                }
            };
        }
        openedDialogs.add(this);
        mZBasedWindow.setResizable(true);
        mZBasedWindow.addOnClose(s -> openedDialogs.remove(this));
        mZBasedWindow.showAndWait();
    }

    public void openAsTab() {
        new ZTb() {
            @Override
            public Parent getView() {
                return ZSystemView.this.getView();
            }

            @Override
            public String getTitle() {
                return ZSystemView.this.getTitle();
            }

            @Override
            public void onReload(source reload_source) throws Exception {
                ZSystemView.this.reload(reload_source);
            }
        }.open();
    }

}
