package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.ctrls.tnd.java.tornadofx.table.MethodValueFactory;

import java.util.function.Function;

public class MethodCol<E, V> extends col<E, V> {
    public MethodCol(String title, Function<E, V> function, double PrefWidth) {
        super(title);
        if (PrefWidth != -1) {
            setPrefWidth(PrefWidth);
        }
        setCellValueFactory(new MethodValueFactory<E, V>() {
            @Override
            public V apply(E o) {
                return function.apply(o);
            }
        });
    }

    public MethodCol(String s, Function<E, V> function) {
        this(s, function, -1);
    }

}
