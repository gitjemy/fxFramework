package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.ctrls.tables.customCols.cells.IndecatorCell;

import javafx.scene.control.TableCell;

public class IndicatorCol<Item> extends col<Item, Integer> {
    public IndicatorCol(String title, applier<Item> val, applier<Item> min, applier<Item> max) {
        super(title);

        setCellFactory(tc -> {
            TableCell cell = new IndecatorCell<>(val, min, max);
            cell.setWrapText(true);
            return cell;
        });

        setStyle("-fx-alignment: CENTER;");
    }

    public IndicatorCol(String title, applier<Item> val, applier<Item> min, applier<Item> max, double size) {
        this(title, val, min, max);
        setPrefWidth(size);
    }

    public interface applier<i> {
        int get_progress(i currentItem);
    }

}
