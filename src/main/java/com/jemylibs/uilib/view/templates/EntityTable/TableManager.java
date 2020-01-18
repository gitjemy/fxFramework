package com.jemylibs.uilib.view.templates.EntityTable;

import com.jemylibs.uilib.ZView.ZFxml;
import com.jemylibs.uilib.ctrls.ManageBox;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TableManager<E> implements ZFxml {

    public HBox controlsBox;
    @FXML
    private TableView<E> table;
    @FXML
    private ManageBox manage_box;

    private Parent parent;

    public static <E> TableManager<E> create() {
        return ZFxml.get("/zres/fx_layout/templates/table_manager.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public ManageBox getManage_box() {
        return manage_box;
    }

    public TableView<E> getTable() {
        return this.table;
    }

    public void setCustomFilter(Node customFilter) {
        this.controlsBox.getChildren().setAll(customFilter);
    }

    public HBox getControlsBox() {
        return controlsBox;
    }
}
