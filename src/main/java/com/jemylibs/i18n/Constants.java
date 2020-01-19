package com.jemylibs.i18n;

import javafx.geometry.NodeOrientation;

import java.util.ListResourceBundle;

public class Constants extends ListResourceBundle {

    private Object[][] contents = {
            //// Layouts
            {"AppName", "Jemy Apps demo App"},
            {"Orientation", NodeOrientation.LEFT_TO_RIGHT.name()},
            /////Texts
            {"price", new Double(75.00)},
            {"currency", "DKK"},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
