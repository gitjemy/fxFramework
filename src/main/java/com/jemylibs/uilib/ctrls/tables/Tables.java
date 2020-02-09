package com.jemylibs.uilib.ctrls.tables;

import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import com.jemylibs.sedb.ZCOL.SqlCol;
import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.ctrls.tables.customCols.PropertyCol;
import com.jemylibs.uilib.ctrls.tables.customCols.col;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Tables {
    static {
        UIController.assertation();
    }

    public static <R> void init_table(TableView<R> table, col<R, ?>... cols) {
        init_table(table, Arrays.asList(cols));
    }

    public static <R> void init_table(TableView<R> table, List<col<R, ?>> cols) {
        table.setPlaceholder(new Label(Application.getApplication().getBundle().getString("TableViewNoData")));
        table.setTableMenuButtonVisible(true);
        table.getColumns().setAll(cols);
    }

    public static <R extends ZSqlRow> void init_table(TableView<R> table, SETable<R> dbtable) {
        init_table(table, create_table_cols(dbtable));
    }

    public static <R extends ZSqlRow> ArrayList<col<R, ?>> create_table_cols(SETable<R> dbtable) {
        return create_table_cols(dbtable.getCols());
    }

    public static <R extends ZSqlRow> ArrayList<col<R, ?>> create_table_cols(SqlCol<R, ?>... cols) {
        return Stream.of(cols)
                .map(Tables::create_table_col)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static <R extends ZSqlRow, V> col<R, V> create_table_col(SqlCol<R, V> col) {
        return new PropertyCol<R, V>(col.getProperty().getTitle(), col.name);
    }

    public static void setTableHeightByRowCount(TableView table, ObservableList data) {
        int rowCount = data.size();
        TableHeaderRow headerRow = (TableHeaderRow) table.lookup("TableHeaderRow");
        double tableHeight = (rowCount * table.getFixedCellSize())
                // add the insets or we'll be short by a few pixels
                + table.getInsets().getTop() + table.getInsets().getBottom()
                // header row has its own (different) height
                + (headerRow == null ? 0 : headerRow.getHeight());

        table.setMinHeight(tableHeight);
        table.setMaxHeight(tableHeight);
        table.setPrefHeight(tableHeight);
    }
}
