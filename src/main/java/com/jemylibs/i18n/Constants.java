package com.jemylibs.i18n;

import javafx.geometry.NodeOrientation;

import java.util.ListResourceBundle;

public class Constants extends ListResourceBundle {

    private Object[][] contents = {
            //// Layouts
            {"AppName", "Jemy Apps demo App"},
            {"OrientationName", NodeOrientation.LEFT_TO_RIGHT.name()},
            {"Orientation", NodeOrientation.LEFT_TO_RIGHT},

            //Dialogs
            {"Button_Ok", "Ok"},
            {"Button_Cancel", "Cancel"},
            {"Button_Yes", "Yes"},
            {"Button_No", "No"},
            {"Danger", "!Be aware"},
            {"Add", "Add"},
            //ui
            {"TableViewNoData", "Empty"},

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
