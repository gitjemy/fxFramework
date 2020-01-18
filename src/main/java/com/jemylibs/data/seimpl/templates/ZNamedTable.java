package com.jemylibs.data.seimpl.templates;

import com.jemylibs.data.seimpl.helpers.ZItem;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.SETable;
import com.jemylibs.sedb.ZCOL.*;
import com.jemylibs.sedb.helpers.JavaSeLink;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ZNamedTable<E extends ZItem> extends SETable<E> {

    private final _String<E> name = new _String<>("name", 250, new WritableProperty<>("الاسم", ZItem::getName, ZItem::setName));

    private final _LastModified<E> last_updated = new _LastModified<>("تاريخ التعديل");
    private final _CreationTime<E> creationTime = new _CreationTime<>("تاريخ الانشاء");

    public ZNamedTable(JavaSeLink link, String TName, _ID_AI<E> ID) {
        super(link, TName, ID);
    }

    public ZNamedTable(JavaSeLink link) {
        super(link, null, new _ID_AI<>());
    }

    @Override
    protected void register(SqlCol... othercols) throws Exception {
        ArrayList<SqlCol> sqlCols = new ArrayList<>(Arrays.asList(othercols));
        sqlCols.add(0, name);
        sqlCols.add(last_updated);
        sqlCols.add(creationTime);
        super.register(sqlCols.toArray(new SqlCol[0]));
    }

    public _String<E> getName() {
        return name;
    }

    public SqlCol<E, ?>[] getFilterCols() {
        return new SqlCol[]{
                this.getName()
        };
    }

    public String getItemName(int itemId) throws Exception {
        return db.value(getName(), itemId);
    }
}
