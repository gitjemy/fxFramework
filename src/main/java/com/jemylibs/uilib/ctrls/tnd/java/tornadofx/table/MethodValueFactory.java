package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.table;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public abstract class MethodValueFactory<E, V> implements Callback<TableColumn.CellDataFeatures<E, V>, ObservableValue<V>>, Function<E, V> {

    public ObservableValue<V> call(TableColumn.CellDataFeatures<E, V> param) {
        final E Item = param == null ? null : param.getValue();
        final V apply = Item == null ? null : apply(Item);
        return new ObservableValueBase<V>() {
            @Override
            public V getValue() {
                return apply;
            }
        };
    }

    @Override
    abstract public V apply(E e);
}
