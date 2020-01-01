package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.Query.ZQ.Like;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class _String<E extends ZSqlRow> extends SqlCol<E, String> {
    public final short Size;

    public _String(String name, int Size, boolean not_null, WritableProperty<E, String> property) {
        super(name, property, not_null);
        this.Size = (short) Size;
    }

    public _String(String name, int Size, WritableProperty<E, String> property) {
        super(name, property, false);
        this.Size = (short) Size;
    }

    public _String(String Name, short size, String title) {
        super(Name, new WritableProperty<>(Name, title));
        this.Size = size;
    }

    @Override
    public String get(ResultSet resultSet) throws SQLException {
        return resultSet.getString(name);
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` VARCHAR(" + Size + ")" + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public Equal equal(String val) {
        return new Equal(this, val);
    }


    public Like like(String val) {
        return new Like(this, val);
    }


}
