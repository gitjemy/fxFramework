package com.jemylibs.data.seimpl.templates.Settings.vars;

import com.jemylibs.data.seimpl.templates.ZNamedTable;
import com.jemylibs.gdb.properties.WritableProperty;
import com.jemylibs.sedb.ZCOL._ID_AI;
import com.jemylibs.sedb.ZCOL._Object;
import com.jemylibs.sedb.helpers.JavaSeLink;

public class SettingsTable extends ZNamedTable<VarValue> {

    public _Object<VarValue, ?> val = new _Object<>("val", 4800,
            new WritableProperty<>("value", VarValue::getValue, VarValue::setValue));

    public SettingsTable(JavaSeLink link) throws Exception {
        super(link, "SettingsTable", new _ID_AI<>());
        register(val);
    }

    @Override
    public VarValue createNewElement() {
        return new VarValue();
    }
}
