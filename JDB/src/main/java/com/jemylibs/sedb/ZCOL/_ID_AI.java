package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.Query.ZQ.NotEqual;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.helpers.MysqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class _ID_AI<E extends ZSqlRow> extends SqlCol<E, Integer> {

    public _ID_AI() {
        this("id");
    }

    public _ID_AI(String name, String title) {
        super(name, new WritableProperty<>(title, E::getId, E::setId));
    }

    public _ID_AI(String name) {
        super(name, new WritableProperty<>("id", E::getId, E::setId));
    }

    @Override
    public Equal equal(Integer val) {
        return new Equal(this, val);
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        if (link instanceof MysqlHelper) {
            CreateTable.first.add("`" + name + "` INT NOT NULL AUTO_INCREMENT");
            CreateTable.last.add("PRIMARY KEY (`" + name + "`)");
        } else {
            CreateTable.first.add("`" + name + "` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT");
        }
    }

    public NotEqual not_equal(Integer val) {
        return new NotEqual(this, val);
    }

    @Override
    final public Integer get(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(name);
    }
}
