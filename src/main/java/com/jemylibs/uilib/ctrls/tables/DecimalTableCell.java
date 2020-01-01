package com.jemylibs.uilib.ctrls.tables;

import com.jemylibs.uilib.ctrls.inputs.DecimalTextField;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.util.Callback;

public class DecimalTableCell extends TableCell<Object, Double> {

    DecimalTextField DecimalTextField = new DecimalTextField();
    EventHandler<TableColumn.CellEditEvent<Object, Double>> OnCommit;

    private DecimalTableCell(EventHandler<TableColumn.CellEditEvent<Object, Double>> OnCommit) {
        this.OnCommit = OnCommit;
        DecimalTextField.setOnAction(v -> {
            commitEdit(DecimalTextField.getDouble());
            cancelEdit();
            getTableView().refresh();
        });
    }

    public static Callback Create(EventHandler<TableColumn.CellEditEvent<Object, Double>> OnCommit) {
        return s -> new DecimalTableCell(OnCommit);
    }

    @Override
    public void commitEdit(Double newValue) {
        OnCommit.handle(new TableColumn.CellEditEvent(getTableView(),
                new TablePosition(getTableView(), getIndex(), getTableColumn()), TableColumn.CellEditEvent.ANY, newValue));
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().toString());
        setGraphic(null);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (getItem() != null) {
            setGraphic(DecimalTextField);
            DecimalTextField.setValue(getItem());
            DecimalTextField.selectAll();
            DecimalTextField.requestFocus();
            setText(null);
        }
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            setText(item.toString());
        }
    }

}
