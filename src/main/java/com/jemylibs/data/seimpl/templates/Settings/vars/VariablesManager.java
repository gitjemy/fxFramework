package com.jemylibs.data.seimpl.templates.Settings.vars;

import com.jemylibs.gdb.Query.ZQ.Selector;
import com.jemylibs.sedb.helpers.JavaSeLink;

import java.util.List;

public class VariablesManager {
    private final SettingsTable table;

    public VariablesManager(JavaSeLink link) throws Exception {
        table = new SettingsTable(link);
    }

    public <E> Var<E> get(String name, E defaultValue) throws Exception {
        try {
            Integer value = table.db.value(table.getID(), new Selector(table.getName().equal(name)));
            return new Var<>(value, this);
        } catch (Exception e) {
            VarValue<E> eVarValue = new VarValue<>();
            eVarValue.setValue(defaultValue);
            eVarValue.setName(name);
            int id = table.insert(eVarValue).getId();
            return new Var<>(id, this);
        }
    }

    public <E> void Update(Var<E> var, E e) {
        try {
            VarValue byId = table.getById(var.getId());
            byId.setValue(e);
            table.update(byId);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public <E> E getValue(Var eVar) throws Exception {
        return (E) table.db.value(table.val, eVar.getId());
    }

    public List<VarValue> listAll() throws Exception {
        return table.list();
    }
}
