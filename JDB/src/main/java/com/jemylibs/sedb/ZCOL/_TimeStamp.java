package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.utility.JDateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class _TimeStamp<E extends ZSqlRow> extends SqlCol<E, LocalDateTime> {

    public _TimeStamp(String Name, WritableProperty<E, LocalDateTime> property) {
        super(Name, property);
    }

    @Override
    public Equal equal(LocalDateTime val) {
        return new Equal(this, JDateTime.DB_TIMESTAMP(val));
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` DATETIME " + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public LocalDateTime get(ResultSet resultSet) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(name);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    @Override
    public Key toDbKey(E i) {
        LocalDateTime ite = property.getValue(i);
        if (ite == null) return new Key<>(name, null);
        return new Key<>(name, Timestamp.valueOf(ite));
    }
}
