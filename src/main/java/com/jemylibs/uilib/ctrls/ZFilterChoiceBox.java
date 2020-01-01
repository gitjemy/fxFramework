package com.jemylibs.uilib.ctrls;

import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import com.jemylibs.sedb.ZCOL.SqlCol;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;


@DefaultProperty("items")
public class ZFilterChoiceBox<Item extends ZSqlRow> extends ChoiceBox<ZSqlRow> {

    private int selected_id;
    private boolean w_s_a = false;
    private int select_id_on_reload = -1;
    private Change change;
    private com.jemylibs.sedb.SETable<Item> SETable;

    public ZFilterChoiceBox() {
        super();
        init();
    }

    public ZFilterChoiceBox(ObservableList<ZSqlRow> items) {
        super(items);
        init();
    }

    public ZFilterChoiceBox(SETable<Item> entity) {
        this();
        init(entity, false, -1, null);
    }

    private void init() {
        getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (selected_id != newValue.getId()) {
                    selected_id = newValue.getId();
                    if (change != null) change.onChange(selected_id);
                }
            }
        });
    }

    public void init(SETable<Item> entity, boolean with_select_all, int select_id_on_reload, Change change) {
        try {
            this.change = change;
            this.select_id_on_reload = select_id_on_reload;
            this.w_s_a = with_select_all;
            this.SETable = entity;
            if (with_select_all) {
                getItems().setAll(new AllItem());
            }
            getItems().addAll(entity.list());
            setSelectedId(select_id_on_reload);
        } catch (Throwable e) {
            ZAlert.errorHandle(e);
        }
    }

    public void init(SETable<Item> entity, boolean with_select_all, Change change) {
        init(entity, with_select_all, -1, change);
    }

    public void init(SETable<Item> entity, boolean with_select_all) {
        init(entity, with_select_all, -1, null);
    }

    public void init(SETable<Item> entity) {
        init(entity, false, -1, null);
    }

    public int getSelectedId() {
        return selected_id;
    }

    public void setSelectedId(int class_id) {
        ObservableList<ZSqlRow> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == class_id) {
                getSelectionModel().select(i);
                break;
            }
        }
    }

    protected void reload() {
        init(SETable, w_s_a, select_id_on_reload, change);
    }

    public Condition getCondition(SqlCol<?, Integer> col) {
        int selectedId = getSelectedId();
        if (selectedId == -1) {
            return null;
        } else {
            return col.equal(selectedId);
        }
    }

    public interface Change {
        void onChange(int id);
    }

    public class AllItem implements ZSqlRow {
        @Override
        public int getId() {
            return -1;
        }

        @Override
        public void setId(int id) {

        }

        @Override
        public String toString() {
            return "الكل";
        }
    }
}
