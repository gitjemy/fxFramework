package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.Query.ZQ.GreaterThan;
import com.jemylibs.gdb.Query.ZQ.LessThan;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.utility.JDateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class _Date<E extends ZSqlRow> extends SqlCol<E, LocalDate> {

    public _Date(String Name, WritableProperty<E, LocalDate> property) {
        super(Name, property);
    }

    @Override
    public Equal equal(LocalDate val) {
        return new Equal(this, JDateTime.DB_TIMESTAMP(val.atStartOfDay()));
    }

    public GreaterThan greaterThan(LocalDate val) {
        return new GreaterThan(this, JDateTime.DB_TIMESTAMP(val.atStartOfDay()));
    }

    public LessThan lessThan(LocalDate val) {
        return new LessThan(this, JDateTime.DB_TIMESTAMP(val.atStartOfDay()));
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` DATETIME " + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public LocalDate get(ResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(name);
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }

    @Override
    public Key toDbKey(E i) {
        LocalDate ite = property.getValue(i);
        if (ite == null) return new Key<>(name, null); /// todo
        return new Key<>(name, Timestamp.valueOf(ite.atStartOfDay()));
    }
}
