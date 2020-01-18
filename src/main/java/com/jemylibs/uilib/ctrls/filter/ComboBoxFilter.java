package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.ZCOL.SqlCol;
import com.jemylibs.uilib.ctrls.util;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ComboBoxFilter<E extends ZSqlRow, V> extends FILTER<ComboBox<V>> {
    private final SqlCol<E, V> col;

    public ComboBoxFilter(SqlCol<E, V> col, List<V> values) {
        super(col.getProperty().getTitle(), util.createComboBox(values));
        getNode().setPromptText("إختر " + col.getProperty().getTitle());
        this.col = col;
    }

    public Condition getCondition() {
        V text = getNode().getSelectionModel().getSelectedItem();
        if (text == null) {
            return null;
        } else {
            return new Equal(col, text);
        }
    }

    @Override
    public Condition getCondition(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        } else {
            return new Equal(col, text);
        }
    }

    @Override
    public void clearValue() {
        getNode().getSelectionModel().select(null);
    }

    @Override
    void setOnChange(Runnable runnable) {
        getNode().setOnKeyReleased(event -> runnable.run());

        getNode().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> runnable.run());
    }
}
