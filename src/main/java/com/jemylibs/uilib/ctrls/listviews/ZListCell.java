package com.jemylibs.uilib.ctrls.listviews;

import java.util.function.Function;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ZListCell<T> extends ListCell<T> {
    private Function<T, Object>[] values;

    private Label[] labels;
    private VBox content = new VBox();

    public ZListCell(Function<T, Object>... values) {
        this.values = values;
        content.setSpacing(2);
        getStyleClass().add("zlist-cell");
        content.setPadding(new Insets(3));
        labels = new Label[values.length];
        for (int i = 0; i < values.length; i++) {
            labels[i] = new Label();
            labels[i].getStyleClass().add("list-cell-label");
            content.getChildren().add(labels[i]);
        }
        if (labels.length > 0) {
            labels[0].getStyleClass().add("title");
        }
    }

    public static <T> Callback<ListView<T>, ListCell<T>> CellFactory(Function<T, Object>... values) {
        return param -> new ZListCell<>(values);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            for (int i = 0; i < values.length; i++) {
                Object s = values[i].apply(item);
                if (s != null) {
                    labels[i].setText(s.toString());
                }
            }
            setGraphic(content);

        }
    }


    public double getCellHeight() {
        return this.labels.length * 25;
    }

    public Function<T, Object>[] getValues() {
        return this.values;
    }
}
