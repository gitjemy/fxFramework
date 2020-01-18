package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;
import com.jemylibs.gdb.utility.StringUtils;

public class GreaterThan extends Condition {
    private final COL col;
    private final Object value;

    public GreaterThan(COL col, Object value) {
        this.col = col;
        this.value = value;
    }

    @Override
    public String getWherePiece() {
        return "`" + col.mtable.TableName + "`.`" + col.name + "`>'" + StringUtils.escapeString(value.toString(), true) + "'";
    }
}
