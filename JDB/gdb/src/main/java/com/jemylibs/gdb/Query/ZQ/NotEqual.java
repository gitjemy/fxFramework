package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;

public class NotEqual extends Condition {
    public final COL col;
    private final Object value;

    public NotEqual(COL col, Object value) {
        this.col = col;
        this.value = value;
    }

    @Override
    public String getWherePiece() {
        if (value == null) {
            return "`" + col.mtable.TableName + "`.`" + col.name + "` IS NOT NULL";
        }
        return "`" + col.mtable.TableName + "`.`" + col.name + "`!='" + value.toString() + "'";
    }
}
