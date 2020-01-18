package com.jemylibs.uilib.ctrls.tables.editable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class TextFieldTableCell<S, T> extends TableCell<S, T> {

    private final EventHandler<TableColumn.CellEditEvent<S, T>> onCommit;
    private TextField textField;
    private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<>(this, "converter");


    public TextFieldTableCell(StringConverter<T> converter, EventHandler<TableColumn.CellEditEvent<S, T>> onCommit) {
        this.onCommit = onCommit;
        this.getStyleClass().add("text-field-table-cell");
        setConverter(converter);
    }

    public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn(EventHandler<TableColumn.CellEditEvent<S, String>> onCommit) {
        return forTableColumn(new DefaultStringConverter(), onCommit);
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(final StringConverter<T> converter, EventHandler<TableColumn.CellEditEvent<S, T>> onCommit) {
        return list -> new TextFieldTableCell<>(converter, onCommit);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }


    @Override
    public void startEdit() {
        if (!isEditable()
                || !getTableView().isEditable()
                || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (textField == null) {
                textField = CellUtils.createTextField(this, getConverter());
            }

            CellUtils.startEdit(this, getConverter(), null, null, textField);
        }
    }

    @Override
    public void commitEdit(T newValue) {
        super.commitEdit(newValue);
        onCommit.handle(new TableColumn.CellEditEvent(getTableView(),
                new TablePosition(getTableView(), getIndex(), getTableColumn()), TableColumn.CellEditEvent.ANY, newValue));
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        CellUtils.cancelEdit(this, getConverter(), null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        CellUtils.updateItem(this, getConverter(), null, null, textField);
    }
}
