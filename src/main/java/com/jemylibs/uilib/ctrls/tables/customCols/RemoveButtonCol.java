package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.utilities.icon.fontIconLib.IconBuilder;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;

import java.util.function.Consumer;

public class RemoveButtonCol<E> extends ButtonCol<E> {

    public RemoveButtonCol(Consumer<E> onClick) {
        super("", FontAwesome.FA_REMOVE, onClick);
        setGraphic(IconBuilder.menu_bar(FontAwesome.FA_TRASH));
    }
}
