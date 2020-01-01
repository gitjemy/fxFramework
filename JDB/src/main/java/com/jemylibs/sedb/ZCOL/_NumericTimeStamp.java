package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class _NumericTimeStamp<E extends ZSqlRow> extends _TimeStamp<E> {

    public _NumericTimeStamp(String Name, WritableProperty<E, LocalDateTime> property) {
        super(Name, property);
    }

    @Override
    public void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` BIGINT " + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public LocalDateTime get(ResultSet resultSet) throws SQLException {
        long value = resultSet.getLong(name);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }

    @Override
    public Key toDbKey(E i) {
        LocalDateTime value = property.getValue(i);
        if (value == null) return new Key<>(name, null);
        long longVal = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new Key<>(name, longVal);
    }

    @Override
    public Equal equal(LocalDateTime val) {
        long l = val.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return new Equal(this, l);
    }

    public Condition between(LocalDateTime from, LocalDateTime to) {
        return new Condition() {
            @Override
            public String getWherePiece() {
                long fVal = (from == null) ? 0 : from.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long tVal = (to == null) ? Long.MAX_VALUE : to.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                return "`" + mtable.TableName + "`.`" + name + "` Between '" + fVal + "' and '" + tVal + "'";
            }
        };
    }
}

