package com.jemylibs.sedb.ZCOL;

import com.jemylibs.gdb.Query.ZQ.Equal;
import com.jemylibs.gdb.Query.ZQ.GreaterThan;
import com.jemylibs.gdb.Query.ZQ.LessThan;
import com.jemylibs.gdb.ZCOL.CreateTable;
import com.jemylibs.gdb.ZCOL.Key;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.gdb.helpers.Link;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.utility.JDouble;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class _Decimal<E extends ZSqlRow> extends SqlCol<E, BigDecimal> {

    public _Decimal(String name, WritableProperty<E, BigDecimal> property) {
        super(name, property);
    }

    @Override
    public Equal equal(BigDecimal val) {
        return new Equal(this, val);
    }

    public LessThan less_than(BigDecimal val) {
        return new LessThan(this, val);
    }

    public GreaterThan greater_than(BigDecimal val) {
        return new GreaterThan(this, val);
    }

    @Override
    protected void create(CreateTable CreateTable, Link link) {
        CreateTable.first.add("`" + name + "` decimal(18,6) " + (not_null ? " NOT NULL" : ""));
    }

    @Override
    public BigDecimal get(ResultSet resultSet) throws SQLException {
        String string = resultSet.getString(name);
        if (string != null) {
            return new BigDecimal(string);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public Key toDbKey(E e) {
        BigDecimal value = property.getValue(e);
        if (value == null) value = BigDecimal.ZERO;
        return new Key<>(name, JDouble.formatForDb(value));
    }
}
