package com.jemylibs.uilib.ctrls.filter;

import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.gdb.Query.ZQ.Condition;
import com.jemylibs.gdb.Query.ZQ.Like;
import com.jemylibs.gdb.Query.ZQ.Selector;
import com.jemylibs.gdb.ZCOL.COL;
import com.jemylibs.gdb.ZSqlRow;
import com.jemylibs.sedb.ZCOL.SqlCol;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ZFilterer<E extends ZSqlRow> extends FlowPane {

    public ArrayList<Condition> hiddenConditions = new ArrayList<>();
    private ArrayList<Filterer<E>> filterers = new ArrayList<>();
    private Consumer<List<E>> filteredItemsConsumer;
    private SqlCol<E, ?>[] filterCols;
    private boolean combined = false;

    public ZFilterer() {
        this.setVgap(5);
        this.setHgap(5);
        setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("z-filterer");
    }

    public void init(Consumer<List<E>> filteredItemsConsumer, SqlCol<E, ?>... filterCols) {
        this.filteredItemsConsumer = filteredItemsConsumer;
        this.filterCols = filterCols;
        init();
    }

    public void init(TableView<E> table, SqlCol<E, ?>... filterCols) {
        this.filteredItemsConsumer = d -> table.getItems().setAll(d);
        this.filterCols = filterCols;
        init();
    }

    public void init(ListView<E> table, SqlCol<E, ?>... filterCols) {
        this.filteredItemsConsumer = d -> table.getItems().setAll(d);
        this.filterCols = filterCols;
        init();
    }

    public boolean isCombined() {
        return combined;
    }

    public void setCombined(boolean combined) {
        this.combined = combined;
        init();
    }

    private void init() {
        getChildren().clear();
        if (combined) {
            initCompinedView();
        } else {
            initUncombined();
        }
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
                for (COL<?, E, ?> eSqlCol : this.filterCols) {
                    conditions.add(new Like(eSqlCol, text));
                }
                filteredItemsConsumer.accept(filterCols[0].mtable.list(new Selector(false, conditions.toArray(new Condition[0]))));
            } catch (Exception e) {
                ZAlert.errorHandle(e);
            }
        });
    }

    public void rePush() {
        ArrayList<Condition> conditions = new ArrayList<>(hiddenConditions);
        for (Filterer<E> item : filterers) {
            Condition condition = item.getCondition();
            if (condition != null) {
                conditions.add(condition);
            }
        }
        try {
            if (!conditions.isEmpty()) {
                Selector where = new Selector(true, conditions.toArray(new Condition[0]));
                filteredItemsConsumer.accept(filterCols[0].mtable.list(where));
            } else {
                filteredItemsConsumer.accept(filterCols[0].mtable.list());
            }
        } catch (Exception e) {
            ZAlert.errorHandle(e);
        }
    }

    private void initUncombined() {
        double itemPrefWidth = 130;
        this.setPrefWrapLength(itemPrefWidth * 15d);
        getChildren().add(new Label("بحث"));

        EventHandler<KeyEvent> onKeyRelease = n -> rePush();

        int i = 0;
        for (SqlCol<E, ?> filterCol : filterCols) {
            Filterer<E> item = new Filterer<>(filterCol.getProperty().getTitle(), filterCol);
            TextField node = item.getNode();
            node.setPrefWidth(itemPrefWidth);
            int finalI = i;
            node.setOnKeyReleased(onKeyRelease);

            node.setOnKeyPressed(event -> {
                if (node.getText().isEmpty()) {
                    if (event.getCode() == KeyCode.BACK_SPACE) {
                        if (finalI != 0) {
                            filterers.get(finalI - 1).getNode().requestFocus();
                        }
                    }
                }
            });
            i++;
            filterers.add(item);
            this.getChildren().add(node);
        }
    }


    public void clearTexts() {
        this.filterers.forEach(eFilterer -> eFilterer.getNode().setText(""));
    }
}
