package com.jemylibs.uilib.ZView.containers.zapplets;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import com.jemylibs.uilib.ctrls.panes.ItemsPane;
import com.jemylibs.uilib.ctrls.panes.PropertyPaneItem;
import com.jemylibs.uilib.view.ZBaseSystemView;
import com.jemylibs.uilib.view.ZSystemView;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ZApplet extends ZSystemView {
    public final int appletWidth;
    final String title;
    final VBox parent;

    Label titleLabel;

    public ZApplet(int appletWidth, String title, Supplier<Region> supplier) {
        this.appletWidth = appletWidth;
        this.title = title;
        titleLabel = new Label(this.title);
        Parent theView = supplier.get();
        this.parent = new VBox(titleLabel, theView);
        this.parent.setPadding(new Insets(10));
        this.parent.setSpacing(5);
        titleLabel.getStyleClass().add("title");
        this.parent.getStyleClass().add("z-applet");
    }

    public static ZApplet fromExisted(int appletWidth, String title, ZBaseSystemView view) {
        return new ZApplet(appletWidth, title, () -> (Region) view.getView()) {
            @Override
            public void onReload(source reload_source) throws Exception {
                view.reload(reload_source);
            }
        };
    }

    public static ZApplet itemsApplet(String title, PropertyPaneItem... items) {
        int appletSize = items.length / 3;
        if (appletSize < 1) {
            appletSize = 1;
        } else if (appletSize > 4) {
            appletSize = 4;
        }
        return new ZApplet(appletSize, title, () -> {
            ItemsPane itemsPane = new ItemsPane();
            itemsPane.setItems(items);
            return itemsPane;
        }) {
            @Override
            public void onReload(source reload_source) throws Exception {
            }
        };
    }

    public static ZApplet buttonsApplet(String title, ObjectTitle<Runnable>... buttons) {
        HBox box = new HBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.getChildren().setAll(Stream.of(buttons).map(d -> {
            Button button = new Button();
            button.setText(d.getTitle());
            button.setOnAction(event -> d.get().run());
            return button;
        }).collect(Collectors.toList()));

        int appletSize = buttons.length / 2;
        if (appletSize < 1) {
            appletSize = 1;
        } else if (appletSize > 4) {
            appletSize = 4;
        }
        return new ZApplet(appletSize, title, () -> box) {
            @Override
            public void onReload(source reload_source) throws Exception {
            }
        };
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    final public Region getView() {
        return parent;
    }
}
