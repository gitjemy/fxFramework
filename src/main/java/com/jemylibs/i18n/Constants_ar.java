package com.jemylibs.i18n;

import javafx.geometry.NodeOrientation;

import java.util.ListResourceBundle;

public class Constants_ar extends ListResourceBundle {

    private Object[][] contents = {
            //// Layouts
            {"AppName", "برامج جيمي : التجربة"},
            {"Orientation", NodeOrientation.RIGHT_TO_LEFT.name()},

            /////Texts
            {"price", new Double(75.00)},
            {"currency", "DKK"},

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
