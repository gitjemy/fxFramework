package com.jemylibs.uilib.ctrls.tables;

import com.jemylibs.uilib.ctrls.inputs.NumbersTextField;
import com.jemylibs.uilib.ctrls.tables.customCols.cells.Cell;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class EditableNumberCell<E> extends Cell<E, Integer> {

    private NumbersTextField numbersTextField = new NumbersTextField();
    private EventHandler<TableColumn.CellEditEvent<E, Integer>> OnCommit;

    private EditableNumberCell(EventHandler<TableColumn.CellEditEvent<E, Integer>> OnCommit) {
        this.OnCommit = OnCommit;
        numbersTextField.setOnAction(v -> {
            commitEdit(numbersTextField.getIntValue());
            cancelEdit();
            getTableView().refresh();
        });
    }

    public static <E> Callback Create(EventHandler<TableColumn.CellEditEvent<E, Integer>> OnCommit) {
        return s -> new EditableNumberCell<>(OnCommit);
    }

    @Override
    public void commitEdit(Integer newValue) {
        TableView<E> tableView = getTableView();
        TablePosition<E, Integer> position = new TablePosition<>(tableView, getIndex(), getTableColumn());
        OnCommit.handle(new TableColumn.CellEditEvent<>(tableView, position,
                (EventType<TableColumn.CellEditEvent<E, Integer>>) TableColumn.CellEditEvent.ANY, newValue));
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem() + "");
        setGraphic(null);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getItem() != null) {
            setGraphic(numbersTextField);
            numbersTextField.setIntValue(getItem());
            numbersTextField.selectAll();
            numbersTextField.requestFocus();
            setText(null);
        }
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            setText(item + "");
        }
    }

}
