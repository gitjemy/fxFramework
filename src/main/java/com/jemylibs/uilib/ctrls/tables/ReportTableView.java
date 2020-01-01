package com.jemylibs.uilib.ctrls.tables;


import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.gdb.Query.ZQ.Selector;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReportTableView<E extends ZSqlRow> extends VBox {

    private HBox additionalControls = new HBox();
    private Pagination pagination = new Pagination();
    private TableView<E> tableView;
    private Function<Integer, List<E>> listFunction = integer -> new ArrayList<>();
    private IntegerProperty pageSize = new SimpleIntegerProperty(20);
    private Selector selector;
    private Runnable onDataReloaded;

    {
        tableView = new TableView<>();
        setPadding(new Insets(5));
        setSpacing(5);
        HBox hBox = new HBox(additionalControls, pagination);
//        HBox.setHgrow(additionalControls, Priority.ALWAYS);
        hBox.setAlignment(Pos.CENTER);
        hBox.getStyleClass().add("z-item-view");
        getChildren().setAll(tableView, hBox);
        setVgrow(tableView, Priority.ALWAYS);

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            reloadPage(newValue.intValue());
        });
    }

    public ReportTableView() {
    }

    public ReportTableView(double spacing) {
        super(spacing);
    }

    public ReportTableView(Node... children) {
        super(children);
    }

    public ReportTableView(double spacing, Node... children) {
        super(spacing, children);
    }

    private void reloadPage(int newValue) {
        List<E> apply = listFunction.apply(newValue);
        getTableView().getItems().setAll(apply);
        if (onDataReloaded != null) {
            onDataReloaded.run();
        }
    }

    public TableView<E> getTableView() {
        return tableView;
    }

    public void reload(SETable<E> table, Selector selector, boolean lastPage) {

        try {
            if (table.count() == 0) {
                return;
            }
        } catch (Exception e) {
            ZAlert.errorHandle(e);
            return;
        }

        if (selector == null) {
            selector = new Selector();
        }
        pagination.setMaxPageIndicatorCount(5);

        selector.clearLimits();
        this.selector = selector;
        try {
            int count = table.db.count(table, selector);
            int pages = (int) Math.ceil((double) count / (double) pageSize.get());
            pagination.setPageCount(pages);
        } catch (Exception e) {
            ZAlert.errorHandle(e);
        }
        this.listFunction = d -> {
            try {
                this.selector.setLimits(pagination.getCurrentPageIndex() * pageSize.get(), pageSize.get());
                return table.list(this.selector);
            } catch (Exception e) {
                ZAlert.errorHandle(e);
            }
            return new ArrayList<>();
        };
        pagination.setCurrentPageIndex(lastPage ? pagination.getPageCount() : 0);

        reloadPage(pagination.getCurrentPageIndex());
    }

    public IntegerProperty pageSizeProperty() {
        return pageSize;
    }

    public void setOnDataReloaded(Runnable onDataReloaded) {
        this.onDataReloaded = onDataReloaded;
    }

    public HBox getAdditionalControls() {
        return additionalControls;
    }
}
