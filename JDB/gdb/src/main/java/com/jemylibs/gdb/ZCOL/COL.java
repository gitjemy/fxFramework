package com.jemylibs.gdb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.gdb.Table;

import java.sql.SQLException;

public abstract class COL<rs, E extends ZSqlRow, V> {

    public final String name;
    protected final WritableProperty<E, V> property;
    protected final boolean not_null;
    public Table<E, rs, ?, ?, ?> mtable;

    protected COL(String Name, WritableProperty<E, V> property, boolean not_null) {
        this.name = Name;
        this.property = property;
        this.not_null = not_null;
    }

    protected COL(String Name, WritableProperty<E, V> property) {
        this.name = Name;
        this.property = property;
        this.not_null = false;
    }

    public Key toDbKey(E e) {
        V value = property.getValue(e);
        return new Key(name, value);
    }

    abstract public void assign(E e, rs resultSet) throws Exception;

    public void setMtable(Table<E, rs, ?, ?, ?> mtable) {
        this.mtable = mtable;
    }

    public boolean exist(V val) throws Exception {
        return mtable.db.exist(equal(val));
    }

    abstract public Equal equal(V val);

    abstract protected void create(CreateTable CreateTable, Link link);

    abstract public V get(rs resultSet) throws SQLException;

    public WritableProperty<E, V> getProperty() {
        return property;
    }

    public V getValueAt(int rowId) throws Exception {
        return mtable.db.value(this, rowId);
    }
}
