package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class _EnumVal<E extends ZSqlRow, enm extends Enum<enm>> extends SqlCol<E, enm> {

    private final Class<enm> mclass;

    public _EnumVal(String Name, Class<enm> mclass, WritableProperty<E, enm> mProperty) {
        super(Name, mProperty);
        this.mclass = mclass;
    }

    public _EnumVal(String Name, Class<enm> mclass, String title) {
        super(Name, new WritableProperty<>(Name, title));
        this.mclass = mclass;
    }

    @Override
    public Equal equal(enm val) {
        return new Equal(this, val.name());
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` VARCHAR(" + 150 + ")" + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public enm get(ResultSet resultSet) throws SQLException {
        String string = resultSet.getString(name);
        if (string == null) {
            return null;
        }
        return Enum.valueOf(mclass, string);
    }

    @Override
    public Key toDbKey(E e) {
        enm value = property.getValue(e);
        if (value == null) {
            return new Key<>(name, null);
        }
        return new Key<>(name, value.name());
    }
}
