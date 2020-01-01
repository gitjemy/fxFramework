package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.RowTypes.CreationTime;

public class _CreationTime<E extends CreationTime & ZSqlRow> extends _TimeStamp<E> {
    public _CreationTime(String title) {
        super("created", new WritableProperty<>(title,
                E::getCreationDateTime,
                E::setCreationDateTime));
    }
}
