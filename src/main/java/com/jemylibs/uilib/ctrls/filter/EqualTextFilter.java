package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.ZCOL.SqlCol;
import javafx.scene.control.TextField;


public class EqualTextFilter<E extends ZSqlRow> extends FILTER<TextField> {

    private final SqlCol<E, ?> col;

    public EqualTextFilter(SqlCol<E, ?> col) {
        super(col.getProperty().getTitle(), create(col));
        this.col = col;
    }

    static TextField create(SqlCol col) {
        TextField textField = new TextField();
        textField.setPromptText(col.getProperty().getTitle());
        return textField;
    }

    public Condition getCondition() {
        String text = getNode().getText();
        if (text == null || text.isEmpty()) {
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
        getNode().setText("");
    }

    @Override
    void setOnChange(Runnable runnable) {
        getNode().setOnKeyReleased(event -> runnable.run());
    }
}
