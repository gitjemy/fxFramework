package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Selector;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.SETable;
import com.jemylibs.sedb.ZCOL.SqlCol;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.utilities.icon.fontIconLib.IconBuilder;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ZFilterer<E extends ZSqlRow> extends FlowPane {

    final private SETable<E> seTable;
    final private Consumer<List<E>> filteredItemsConsumer;
    final private List<FILTER<?>> filterers;

    public ArrayList<Condition> hiddenConditions = new ArrayList<>();
    double itemPrefWidth = 150;
    private Predicate<E> filter;

    public ZFilterer(boolean combined, SETable<E> seTable, Consumer<List<E>> filteredItemsConsumer, FILTER<?>... filterers) {
        this(combined, seTable, filteredItemsConsumer, new ArrayList<>(Arrays.asList(filterers)));
    }

    public ZFilterer(boolean combined, SETable<E> seTable, Consumer<List<E>> filteredItemsConsumer, List<FILTER<?>> filterers) {
        this.seTable = seTable;
        this.filteredItemsConsumer = filteredItemsConsumer;
        this.setVgap(5);
        this.setHgap(5);
        setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("z-filterer");
        this.filterers = filterers;
        init(combined);
    }

    public ZFilterer(boolean combined, SETable<E> seTable, Consumer<List<E>> filteredItemsConsumer, SqlCol<E, ?>... filterers) {
        this(combined, seTable, filteredItemsConsumer, Arrays.stream(filterers).map(LikeTextFilter::new).collect(Collectors.toList()));
    }

    public Predicate<E> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<E> filter) {
        this.filter = filter;
    }

    private void init(boolean combined) {
        getChildren().clear();
        if (combined) {
            initCompinedView();
        } else {
            initUncombined();
        }
        setItemPrefWidth(itemPrefWidth);
    }

    private void initCompinedView() {
        this.setPrefWrapLength(getPrefWidth());

        TextField item = new TextField();
        item.setPromptText("بحث");

        this.getChildren().add(item);

        item.setOnKeyReleased(r -> {
            try {
                String text = item.getText();
                ArrayList<Condition> conditions = new ArrayList<>(hiddenConditions);
                for (FILTER<?> eSqlCol : this.filterers) {
                    conditions.add(eSqlCol.getCondition(text));
                }
                List<E> list = seTable.list(new Selector(false, conditions.toArray(new Condition[0])));
                filteredItemsConsumer.accept(filter(list));
            } catch (Exception e) {
                ZAlert.errorHandle(e);
            }
        });
    }

    public void rePush() {
        ArrayList<Condition> conditions = new ArrayList<>(hiddenConditions);
        for (FILTER<?> item : filterers) {
            Condition condition = item.getCondition();
            if (condition != null) {
                conditions.add(condition);
            }
        }
        try {
            List<E> list;
            if (!conditions.isEmpty()) {
                Selector where = new Selector(true, conditions.toArray(new Condition[0]));
                list = seTable.list(where);
            } else {
                list = seTable.list();
            }
            filteredItemsConsumer.accept(filter(list));
        } catch (Exception e) {
            ZAlert.errorHandle(e);
        }
    }

    private final List<E> filter(List<E> list) {
        if (filter == null)
            return list;
        else return list.stream().filter(filter).collect(Collectors.toList());
    }

    public void setItemPrefWidth(double itemPrefWidth) {
        this.itemPrefWidth = itemPrefWidth;
        this.setPrefWrapLength(itemPrefWidth * 15d);
    }

    private void initUncombined() {
        Label label = new Label("بحث");
        label.setGraphic(IconBuilder.menu_bar(FontAwesome.FA_FILTER));

        getChildren().add(label);

        Runnable onKeyRelease = this::rePush;

        int i = 0;

        for (FILTER<?> item : filterers) {
            Region node = item.getNode();
            node.setPrefWidth(itemPrefWidth);
            int finalI = i;
            item.setOnChange(onKeyRelease);

            node.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (node instanceof TextInputControl) {
                        if (((TextInputControl) node).getText().isEmpty()) {
                            if (finalI != 0) {
                                filterers.get(finalI - 1).getNode().requestFocus();
                            }
                        }
                    } else {
                        if (finalI != 0) {
                            filterers.get(finalI - 1).getNode().requestFocus();
                        }
                    }
                }
            });

            i++;
            this.getChildren().add(node);
        }
    }


    public void clearTexts() {
        this.filterers.forEach(FILTER::clearValue);
    }
}
