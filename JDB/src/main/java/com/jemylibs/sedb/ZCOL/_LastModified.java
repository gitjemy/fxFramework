package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.RowTypes.LastModified;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class _LastModified<E extends LastModified & ZSqlRow> extends _TimeStamp<E> {

    public _LastModified(String title) {
        super("last_modified", new WritableProperty<>(title, E::getLastModified, E::setLastModified));
    }

    @Override
    public Key toDbKey(E i) {
        return new Key<>(name, Timestamp.valueOf(LocalDateTime.now()));
    }
}
