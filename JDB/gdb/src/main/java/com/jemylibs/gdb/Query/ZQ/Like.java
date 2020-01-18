package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;
import com.jemylibs.gdb.utility.StringUtils;

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
            return null;
        }
        return "`" + col.mtable.TableName + "`.`" + col.name + "` Like '%" + StringUtils.escapeString(value.toString(), true) + "%'";
    }
}
