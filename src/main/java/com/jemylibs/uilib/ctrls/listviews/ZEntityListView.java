package com.jemylibs.uilib.ctrls.listviews;

import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import com.jemylibs.uilib.ctrls.filter.ZFilterer;
import com.jemylibs.uilib.utilities.ZValidate;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class ZEntityListView<I extends ZSqlRow> extends VBox {

    private ZFilterer<I> filterTextField;
    private ListView<I> list = new ListView<>();

    public void init(boolean multiSelect, SETable<I> table, final Function<I, Object>... values) {

        filterTextField = new ZFilterer<I>(true,
                table, is -> {
            getList().getItems().setAll(is);
            list.getSelectionModel().selectFirst();
        }, table.getFilterCols());

        getStyleClass().add("ZEntityListView");
        list.setCellFactory(param -> new ZListCell<>(values));
        getChildren().add(filterTextField);

        if (multiSelect) {
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            Button sel_all = new Button("الكل");
//            Button de_sel_all = new Button("إلغاء الكل");
            HBox s = new HBox(sel_all);
            s.setSpacing(5);
//            de_sel_all.setOnAction(e -> list.getSelectionModel().clearSelection());
            sel_all.setOnAction(i -> list.getSelectionModel().selectAll());
            getChildren().add(s);
        }

        setSpacing(5);
        setPadding(new Insets(5));
        getChildren().add(list);
        setVgrow(list, Priority.ALWAYS);

        filterTextField.rePush();
    }

    public int getSelectedId() {
        I selectedItem = list.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            return selectedItem.getId();
        return -1;
    }

    public void setSelectedId(int selectedId) {
        filterTextField.clearTexts();
        if (selectedId != -1) {
            for (I item : list.getItems()) {
                if (item.getId() == selectedId) list.getSelectionModel().select(item);
            }
        }
    }

    public void validateSelection() throws ZValidate {
        ZValidate.ValidSelectionEmpty(list.getSelectionModel(), list);
    }

    public ListView<I> getList() {
        return list;
    }
}
