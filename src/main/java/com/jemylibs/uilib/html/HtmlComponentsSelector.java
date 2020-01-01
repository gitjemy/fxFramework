package com.jemylibs.uilib.html;

import com.jemylibs.uilib.html.base.ZHtmlPage;
import com.jemylibs.uilib.html.base.zhtmlComponent;
import com.jemylibs.uilib.ZView.ZFxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

public class HtmlComponentsSelector implements ZFxml {

    @FXML
    Button unselectallButton;
    @FXML
    Button selectallButton;
    @FXML
    TreeView<zhtmlComponent> treeView;

    private ZHtmlPage page;
    private Parent parent;

    public static HtmlComponentsSelector getInstance(ZHtmlPage page) {
        HtmlComponentsSelector htmlComponentsSelector = ZFxml.get("/zres/fx_layout/html_components_selector.fxml");
        htmlComponentsSelector.setPage(page);
        return htmlComponentsSelector;
    }

    private final void injectChildren(zhtmlComponent component, TreeItem<zhtmlComponent> root) {
        for (Object o : component) {
            if (o instanceof zhtmlComponent) {
                CheckBoxTreeItem<zhtmlComponent> item = new CheckBoxTreeItem<>((zhtmlComponent) o);
                item.setSelected(!((zhtmlComponent) o).isHidden());

                item.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    ((zhtmlComponent) o).setHidden(!newValue);
                });
                root.getChildren().add(item);
                injectChildren((zhtmlComponent) o, item);
            }
        }
    }

    @Override
    public void setView(Parent parent) {
        this.parent = parent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void reload() {
        TreeItem<zhtmlComponent> root = new TreeItem<>();
        injectChildren(page, root);
        treeView.setRoot(root);
        treeView.setCellFactory(param -> new CheckBoxTreeCell<>());
        treeView.setShowRoot(false);
    }

    public ZHtmlPage getPage() {
        return page;
    }

    public void setPage(ZHtmlPage page) {
        this.page = page;
        reload();
    }

    public Parent getParent() {
        return parent;
    }
}
