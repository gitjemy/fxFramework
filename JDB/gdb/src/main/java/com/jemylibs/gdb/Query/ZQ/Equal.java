package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;
import com.jemylibs.gdb.utility.StringUtils;

public class Equal extends Condition {
    public final COL col;
    private final Object value;

    public Equal(COL col, Object value) {
        this.col = col;
        this.value = value;
    }

    @Override
    public String getWherePiece() {
        if (value != null) {
            return "`" + col.mtable.TableName + "`.`" + col.name + "`='" + StringUtils.escapeString(value.toString(), true) + "'";
        } else {
            return "`" + col.mtable.TableName + "`.`" + col.name + "` is null ";
        }
    }
}
