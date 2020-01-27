package com.jemylibs.uilib.ctrls;

import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class ZMenu extends MenuItem {
    public ZMenu(String title, FIcon icon, EventHandler<ActionEvent> action) {
        super(title);
        if (icon != null) setGraphic(icon.menu());
        setOnAction(action);
    }

    public ZMenu(String title, EventHandler<ActionEvent> action) {
        this(title, null, action);
    }
}
