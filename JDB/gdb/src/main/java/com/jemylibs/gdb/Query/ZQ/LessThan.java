package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;

public class LessThan extends Condition {
    private final COL col;
    private final Object value;

    public LessThan(COL col, Object value) {
        this.col = col;
        this.value = value;
    }

    @Override
    public String getWherePiece() {
        return "`" + col.mtable.TableName + "`.`" + col.name + "`<'" + value.toString() + "'";
    }
}
