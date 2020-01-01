package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;

public class Like extends Condition {
    private final COL col;
    private final Object value;

    public Like(COL col, Object value) {
        this.col = col;
        this.value = value;
    }

    @Override
    public String getWherePiece() {
        if (value == null) {
            return "`" + col.mtable.TableName + "`.`" + col.name + "` Like '%" + "" + "%'";
        }
        return "`" + col.mtable.TableName + "`.`" + col.name + "` Like '%" + value.toString() + "%'";
    }
}
