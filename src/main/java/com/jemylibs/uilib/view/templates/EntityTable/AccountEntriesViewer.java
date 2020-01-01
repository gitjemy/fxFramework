package com.jemylibs.uilib.view.templates.EntityTable;

import com.jemylibs.uilib.ZView.ZFxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;

public class AccountEntriesViewer<MainTableItem, SubTableItem> implements ZFxml {
    public TableView<MainTableItem> mainTableView;
    public TableView<SubTableItem> subTableView;
    public FlowPane otherControls;
    @FXML
    Label title;

    private Parent parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Parent getView() {
        return parent;
    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
