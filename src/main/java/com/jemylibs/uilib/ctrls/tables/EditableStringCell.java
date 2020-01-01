package com.jemylibs.uilib.ctrls.tables;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class EditableStringCell extends TableCell<Object, String> {

    TextField DecimalTextField = new TextField();
    EventHandler<TableColumn.CellEditEvent<Object, String>> OnCommit;

    private EditableStringCell(EventHandler<TableColumn.CellEditEvent<Object, String>> OnCommit) {
        this.OnCommit = OnCommit;
        DecimalTextField.setOnAction(v -> {
            commitEdit(DecimalTextField.getText());
            cancelEdit();
            getTableView().refresh();
        });
    }

    public static Callback Create(EventHandler<TableColumn.CellEditEvent<Object, String>> OnCommit) {
        return s -> new EditableStringCell(OnCommit);
    }

    @Override
    public void commitEdit(String newValue) {
        OnCommit.handle(new TableColumn.CellEditEvent(getTableView(),
                new TablePosition(getTableView(), getIndex(), getTableColumn()), TableColumn.CellEditEvent.ANY, newValue));
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getItem() != null) {
            setGraphic(DecimalTextField);
            DecimalTextField.setText(getItem());
            DecimalTextField.selectAll();
            DecimalTextField.requestFocus();
            setText(null);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            setText(item);
        }
    }

}
