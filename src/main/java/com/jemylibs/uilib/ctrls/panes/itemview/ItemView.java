package com.jemylibs.uilib.ctrls.panes.itemview;

import com.jemylibs.uilib.ctrls.panes.PropertyPane;
import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionModel;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Function;

public class ItemView<E> extends StackPane {
    final private PropertyPane mItemPane = new PropertyPane();
    final private Label title = new Label();
    final private Label sub_title = new Label();

    private Function<E, String> sub_title_value;
    private Item<E, ?>[] mItems;

    {
        ///title-view
        title.setText("البيانات");
        title.setMaxWidth(Double.MAX_VALUE);
        sub_title.setText("--");
        title.getStyleClass().add("title");
        sub_title.getStyleClass().add("sub-title");
        VBox s = new VBox(title, sub_title);
        s.getStyleClass().add("title-view");
        s.setSpacing(10);
        s.setPadding(new Insets(5));

        VBox vBox = new VBox(s, mItemPane);
        VBox.setVgrow(mItemPane, Priority.ALWAYS);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(8));
        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.getStyleClass().add("z-item-view");

        getChildren().setAll(scrollPane);
        setPadding(new Insets(20));
        scrollPane.setFitToWidth(true);
    }

    public ItemView() {
        super();
    }

    public void init(String main_tile, Function<E, String> sub_title, Item<E, ?>... itms) {
        mItems = itms;
        PropertyPaneItem[] views = new PropertyPaneItem[itms.length];
        for (int i = 0; i < itms.length; i++) {
            Item<E, ?> itm = itms[i];
            views[i] = new PropertyPaneItem<>(itm.getText(), itm.getView());
        }
        mItemPane.setItems(views);
        this.sub_title_value = sub_title;
        this.title.setText(main_tile);
    }

    public void view(E i) {
        if (i != null) {
            sub_title.setText(sub_title_value.apply(i));
            for (Item<E, ?> mItem : mItems) {
                mItem.item_update(i);
            }
        }
    }

    public void bind(SelectionModel<E> model) {
        model.selectedItemProperty().addListener((observable, oldValue, newValue) -> view(newValue));
    }
}
