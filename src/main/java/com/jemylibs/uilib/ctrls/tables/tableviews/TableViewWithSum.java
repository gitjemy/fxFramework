package com.jemylibs.uilib.ctrls.tables.tableviews;

import com.jemylibs.uilib.ctrls.tables.Tables;
import com.jemylibs.uilib.ctrls.tables.customCols.col;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class TableViewWithSum<E> extends BorderPane {

    private TableView<E> tableView = new TableView<>();
    private TableView<E> sumTableView = new TableView<>();

    public TableViewWithSum() {
        setCenter(tableView);
        setBottom(sumTableView);
    }

    public TableView<E> getTableView() {
        return tableView;
    }

    public TableView<E> getSumTableView() {
        return sumTableView;
    }

    public void setCols(col<E, ?>... cols) {
        Tables.init_table(getTableView(), cols);
        Tables.init_table(getSumTableView(), cols);
    }
}
