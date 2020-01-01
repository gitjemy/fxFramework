package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class _Boolean<E extends ZSqlRow> extends SqlCol<E, Boolean> {
    private final short Size;

    public _Boolean(String name, boolean not_null, WritableProperty<E, Boolean> property) {
        super(name, property, not_null);
        this.Size = 11;
    }

    public _Boolean(String name, WritableProperty<E, Boolean> property) {
        super(name, property, false);
        this.Size = 11;
    }

    @Override
    public Equal equal(Boolean val) {
        return new Equal(this, (val ? 1 : 0));
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` INTEGER(" + Size + ")" + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public Boolean get(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(name) == 1;
    }
}
