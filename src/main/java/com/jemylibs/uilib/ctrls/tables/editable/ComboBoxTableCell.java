package com.jemylibs.uilib.ctrls.tables.editable;

import com.jemylibs.sedb.utility.JDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.LocalDate;

public class ComboBoxTableCell<S> extends TableCell<S, LocalDate> {

    private DatePicker datePicker;

    private BooleanProperty comboBoxEditable = new SimpleBooleanProperty(this, "comboBoxEditable");

    public ComboBoxTableCell() {
        this.getStyleClass().add("date-picker-table-cell");
    }

    public static <S> Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> forTableColumn() {
        return list -> new ComboBoxTableCell<S>();
    }


    public final BooleanProperty comboBoxEditableProperty() {
        return comboBoxEditable;
    }

    public final boolean isComboBoxEditable() {
        return comboBoxEditableProperty().get();
    }

    public final void setComboBoxEditable(boolean value) {
        comboBoxEditableProperty().set(value);
    }


    @Override
    public void startEdit() {
        if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
            return;
        }

        if (datePicker == null) {
            datePicker = CellUtils.createDatePicker(this);
            datePicker.editableProperty().bind(comboBoxEditableProperty());
        }
        datePicker.setValue(getItem());
        super.startEdit();
        setText(null);
        setGraphic(datePicker);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(JDateTime.str_date(getItem()));
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        CellUtils.updateItem(this, null, null, datePicker);
    }
}
