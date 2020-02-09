package com.jemylibs.i18n;

import javafx.geometry.NodeOrientation;

import java.util.ListResourceBundle;

public class Constants_ar extends ListResourceBundle {

    private Object[][] contents = {
            //// Layouts
            {"AppName", "برامج جيمي : التجربة"},
            {"OrientationName", NodeOrientation.RIGHT_TO_LEFT.name()},
            {"Orientation", NodeOrientation.RIGHT_TO_LEFT},

            /////Texts
            {"Button_Ok", "موافق"},
            {"Button_Cancel", "إلغاء"},
            {"Button_Yes", "نعم"},
            {"Button_No", "لا"},
            {"Danger", "إنتبه"},
            {"Add", "إضافة"},
            //ui
            {"TableViewNoData", "فارغ"},

    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
