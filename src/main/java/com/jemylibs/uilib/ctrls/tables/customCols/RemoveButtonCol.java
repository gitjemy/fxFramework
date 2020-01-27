package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class RemoveButtonCol<E> extends ButtonCol<E> {

    public RemoveButtonCol(Consumer<E> onClick) {
        super("", () -> FontAwesome.FA_REMOVE.mk(new Color(1, 1, 1, 1)), onClick);
        icon(FontAwesome.FA_TRASH);
        getStyleClass().add("RemoveButtonCol");
    }
}
