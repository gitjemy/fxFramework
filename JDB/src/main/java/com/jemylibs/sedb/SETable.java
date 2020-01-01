package com.jemylibs.sedb;

import com.jemylibs.gdb.Table;
import com.jemylibs.gdb.ZCOL.COL;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.ZCOL.SqlCol;
import com.jemylibs.sedb.ZCOL._ID_AI;
import com.jemylibs.sedb.helpers.JavaSeLink;

import java.sql.ResultSet;
import java.util.ArrayList;

abstract public class SETable<Item extends ZSqlRow> extends Table<Item, ResultSet, JavaSeLink,
        _ID_AI<Item>, SqlCol<Item, ?>> {
    public SETable(JavaSeLink link) {
        super(link, null, new _ID_AI<>());
    }

    public SETable(JavaSeLink link, String TName, _ID_AI<Item> ID) {
        super(link, TName, ID);
    }

    public SqlCol[] getFilterCols() {
        return new SqlCol[]{
                this.getID()
        };
    }

    protected void register(SqlCol<Item, ?>... otherCols) throws Exception {
        ArrayList<COL> cols = new ArrayList<>();
        cols.add(getID());
        for (SqlCol col : otherCols) {
            cols.add(col);
            col.setMtable(this);
        }
        setCols(cols.toArray(new SqlCol[0]));
        db.registerTable(this, cols);
    }


}
