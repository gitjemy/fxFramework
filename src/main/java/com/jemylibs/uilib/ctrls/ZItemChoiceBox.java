package com.jemylibs.uilib.ctrls;

import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;


@DefaultProperty("items")
public class ZItemChoiceBox<Item extends ZSqlRow> extends ChoiceBox<Item> {

    private int selected_id;
    private int select_id_on_reload = -1;
    private Change change;
    private com.jemylibs.sedb.SETable<Item> SETable;

    public ZItemChoiceBox() {
        super();
        init();
    }

    public ZItemChoiceBox(ObservableList<Item> items) {
        super(items);
        init();
    }

    public ZItemChoiceBox(SETable<Item> entity) {
        this();
        init(entity, -1, null);
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

    public void init(SETable<Item> entity, int select_id_on_reload, Change change) {
        try {
            this.change = change;
            this.select_id_on_reload = select_id_on_reload;
            this.SETable = entity;

            getItems().addAll(entity.list());
            setSelectedId(select_id_on_reload);
        } catch (Exception e) {
            ZAlert.errorHandle(e);
        }
    }

    public void init(SETable<Item> entity, Change change) {
        init(entity, -1, change);
    }

    public void init(SETable<Item> entity) {
        init(entity, -1, null);
    }

    public int getSelectedId() {
        return selected_id;
    }

    public void setSelectedId(int class_id) {
        ObservableList<Item> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == class_id) {
                getSelectionModel().select(i);
                break;
            }
        }
    }

    protected void reload() {
        init(SETable, select_id_on_reload, change);
    }

    public interface Change {
        void onChange(int id);
    }
}
