package com.jemylibs.data.seimpl.templates.Settings.vars;


public class Var<E> {
    final int id;
    private final VariablesManager variablesManager;

    public Var(int id, VariablesManager variablesManager) {
        this.id = id;
        this.variablesManager = variablesManager;
    }

    public E getValue() {
        try {
            return variablesManager.getValue(this);
        } catch (Exception e) {
            return null;
        }
    }

    public void setValue(E e) {
        variablesManager.Update(this, e);
    }

    public int getId() {
        return this.id;
    }
}
