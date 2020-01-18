package com.jemylibs.uilib.ctrls;

import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.ZView.containers.ZTb;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ZTbMenu extends ZMenu {

    private final static EventHandler<ActionEvent> Fxml_HANDLER = event -> {
        ZTbMenu source = (ZTbMenu) event.getSource();
        ZTb ztab = ZFxml.get((source).getUserData().toString());
        ztab.setText(source.getText());
        ztab.open();
    };

    private final static EventHandler<ActionEvent> Class_HANDLER = event -> {
        try {
            Class source = (Class) ((ZTbMenu) event.getSource()).getUserData();
            ZTb zTb = (ZTb) source.newInstance();
            zTb.open();
        } catch (InstantiationException | IllegalAccessException e) {
            ZAlert.errorHandle(e);
        }
    };

    public ZTbMenu(String title, String fxmlTab) {
        super(title, null, Fxml_HANDLER);
        setUserData(fxmlTab);
    }

    public ZTbMenu(String title, FIcon icn, String fxmlTab) {
        super(title, icn, Fxml_HANDLER);
        setUserData(fxmlTab);
    }

    public <e extends ZTb> ZTbMenu(String title, Class<e> aClass) {
        super(title, null, Class_HANDLER);
        setUserData(aClass);
    }

    public <e extends ZTb> ZTbMenu(String title, FIcon icn, Class<e> aClass) {
        super(title, icn, Class_HANDLER);
        setUserData(aClass);
    }
}
