package com.jemylibs.uilib.ctrls;

import com.jemylibs.uilib.utilities.icon.IconBuilder;
import com.jemylibs.uilib.utilities.icon.IconFont;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class ZMenu extends MenuItem {
    public ZMenu(String title, IconFont icon, EventHandler<ActionEvent> action) {
        super(title);
        if (icon != null) setGraphic(IconBuilder.menu_bar(icon));
        setOnAction(action);
    }

    public ZMenu(String title, EventHandler<ActionEvent> action) {
        this(title, null, action);
    }
}
