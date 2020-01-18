package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.ctrls.tables.customCols.cells.StringCell;
import com.jemylibs.gdb.properties.Func;

import java.util.function.Function;

public class MethodCol<T, X> extends col<T, X> {

    public MethodCol(String title, Function<T, X> function, double PrefWidth) {
        super(title);
        if (PrefWidth != -1) {
            setPrefWidth(PrefWidth);
        }
        setCellFactory(tc -> new StringCell<>(r -> {
            X apply = function.apply(r);
            if (apply == null) {
                return "";
            }
            return apply + "";
        }));
        setSortable(false);
    }


    public MethodCol(String s, Func<T, X> function) {
        this(s, function::apply, -1);
    }

}
