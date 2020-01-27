package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class IndexCol<R> extends col<R, Object> {

    public IndexCol() {
        super("#");

        setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        setCellFactory(new Callback<TableColumn<R, Object>, TableCell<R, Object>>() {
            @Override
            public TableCell<R, Object> call(TableColumn<R, Object> param) {
                return new TableCell<R, Object>() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (this.getTableRow() != null && item != null) {
                            setText((this.getTableRow().getIndex() + 1) + "");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        setSortable(false);
        setMaxWidth(45);
        icon(FontAwesome.FA_SORT_ASC);
    }
}
