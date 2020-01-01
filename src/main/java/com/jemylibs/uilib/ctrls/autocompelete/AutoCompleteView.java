package com.jemylibs.uilib.ctrls.autocompelete;

import com.jemylibs.uilib.utilities.stringfilter.Filterer;
import com.jemylibs.uilib.utilities.stringfilter.SimpleFilterer;
import com.jemylibs.uilib.ctrls.listviews.ZListCell;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteView<T> extends ComboBox<AutoCompleteItem<T>> {
    private final Function<T, String> itemToString;
    private ListView<AutoCompleteItem<T>> lv;
    private ObservableList<AutoCompleteItem<T>> items = getAutoCompleteItems(new ArrayList<>());
    private Filterer<AutoCompleteItem<T>> filterer;

    public AutoCompleteView(Supplier<ZListCell<T>> listCell, Function<T, String> itemToString, SimpleFilterer<T>... filterer) {
        this.itemToString = itemToString;
        setFilterer(filterer);
        AtomicReference<Double> cellHeight = new AtomicReference<>(null);

        ZListCell<T> apply = listCell.get();
        Function<T, Object>[] values = apply.getValues();
        ArrayList<Function<AutoCompleteItem<T>, Object>> list = new ArrayList<>();
        for (Function<T, Object> value : values) {
            list.add(tAutoCompleteItem -> value.apply(tAutoCompleteItem.get()));
        }
        Function[] functions = list.toArray(new Function[0]);
        Function<ListView<AutoCompleteItem<T>>, ZListCell<AutoCompleteItem<T>>> listCellFacto = auto -> new ZListCell<>(functions);

        getStyleClass().add("auto-complete");

        addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (!isShowing()) return;
            setEditable(true);
            getEditor().clear();
        });

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.SPACE) {
                if (!isShowing()) {
                    show();
                } else {
                    hide();
                }
                e.consume();
            }
        });

        AtomicReference<AutoCompleteItem<T>> selectedItem = new AtomicReference<>(null);
        setPrefHeight(30);
        setHeight(getPrefHeight());
        setMaxHeight(getPrefHeight());
        setMinHeight(getPrefHeight());

        showingProperty().addListener((observable, oldValue, newValue) ->
        {
            lv = ((ComboBoxListViewSkin<AutoCompleteItem<T>>) getSkin()).getListView();
            if (newValue) {
                AtomicReference<ZListCell> firstElement = new AtomicReference<>(null);
                lv.setCellFactory(t -> {
                    ZListCell<AutoCompleteItem<T>> apply1 = listCellFacto.apply(t);
                    if (firstElement.get() == null) {
                        firstElement.set(apply1);
                    }
                    return apply1;
                });
                Platform.runLater(() -> {
                    if (selectedItem.get() == null) {
                        if (firstElement.get() != null) {
                            double cellH = firstElement.get().getCellHeight() + 2;
                            cellHeight.set(cellH);
                            lv.setFixedCellSize(cellH);
                            lv.setPrefHeight(cellH * 6);
                        }
                    }
                });
                lv.scrollTo(this.getValue());

            } else {
                AutoCompleteItem<T> value = this.getValue();
                if (value != null) selectedItem.set(value);
                this.setEditable(false);

                Platform.runLater(() -> {

                    setPrefHeight(30);
                    setHeight(getPrefHeight());
                    setMaxHeight(getPrefHeight());
                    setMinHeight(getPrefHeight());

                    this.getSelectionModel().select(selectedItem.get());
                    this.setValue(selectedItem.get());
                });
            }
        });

        this.setOnHidden(event -> items.forEach(item -> item.setHidden(false)));


        this.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!this.isShowing()) return;
            Platform.runLater(() -> {
                if (this.getSelectionModel().getSelectedItem() == null) {
                    items.forEach(item -> item.setHidden(!this.filterer.isIn(newValue, item)));
                } else {
                    boolean validText = false;
                    for (AutoCompleteItem item : items) {
                        if (item.getTitle().equals(newValue)) {
                            validText = true;
                            break;
                        }
                    }
                    if (!validText) this.getSelectionModel().select(null);
                }

            });
        });

    }

    public void setItems(List<T> items, boolean selectFirstIfFound) {
        this.items = getAutoCompleteItems(items);
        FilteredList<AutoCompleteItem<T>> filteredHideableItems = new FilteredList<>(this.items, t -> !t.isHidden());
        setItems(filteredHideableItems);
        if (selectFirstIfFound) {
            getSelectionModel().selectFirst();
        }
    }

    private ObservableList<AutoCompleteItem<T>> getAutoCompleteItems(List<T> items) {
        ObservableList<AutoCompleteItem<T>> hideableHideableItems
                = FXCollections.observableArrayList(hideableItem -> new Observable[]{hideableItem.hiddenProperty()});
        items.forEach(item -> {
            AutoCompleteItem<T> hideableItem = new AutoCompleteItem<>(itemToString.apply(item), item);
            hideableHideableItems.add(hideableItem);
        });
        return hideableHideableItems;
    }

    private void setFilterer(SimpleFilterer<T>... filterer) {
        this.filterer = (text, item) -> {
            for (SimpleFilterer<T> tSimpleFilterer : filterer) {
                if (tSimpleFilterer.isIn(text, item)) return true;
            }
            return false;
        };
    }

}
