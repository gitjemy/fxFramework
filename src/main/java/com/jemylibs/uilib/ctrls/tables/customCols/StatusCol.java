package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.ctrls.tables.customCols.cells.StatusCell;
import javafx.scene.control.TableCell;

import java.util.function.Function;

public class StatusCol<Item> extends col<Item, Integer> {

    public StatusCol(String title, Function<Item, Status> statusFunction) {
        super(title);

        setCellFactory(tc -> {
            TableCell cell = new StatusCell<>(statusFunction);
            cell.setId("SSs");
            cell.setWrapText(true);
            return cell;
        });

        setStyle("-fx-alignment: CENTER;");
    }
}
