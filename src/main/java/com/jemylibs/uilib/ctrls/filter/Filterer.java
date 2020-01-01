package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Like;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.ZCOL.SqlCol;
import javafx.scene.control.TextField;


public class Filterer<E extends ZSqlRow> extends PropertyPaneItem<TextField> {

    private final SqlCol<E, ?> col;

    public Filterer(String Title, SqlCol<E, ?> col) {
        super(Title, create(col));
        this.col = col;
    }

    static TextField create(SqlCol col) {
        TextField textField = new TextField();
        textField.setPromptText(col.getProperty().getTitle());
        return textField;
    }

    public SqlCol<E, ?> getCol() {
        return col;
    }

    public Condition getCondition() {
        String text = getNode().getText();
        if (text == null || text.isEmpty()) {
            return null;
        } else {
            return new Like(col, text);
        }
    }
}
