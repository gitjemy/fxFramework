package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.cell.PropertyValueFactory;

public class PropertyCol<E, V> extends col<E, V> {
    public PropertyCol(String s, String propertyName) {
        super(s);
        setCellValueFactory(new PropertyValueFactory<>(propertyName));
    }
}
