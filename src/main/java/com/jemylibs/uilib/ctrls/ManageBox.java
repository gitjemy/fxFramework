package com.jemylibs.uilib.ctrls;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import com.jemylibs.gdb.properties.Func;
import com.jemylibs.uilib.utilities.ZValidate;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.Consumer;

public class ManageBox extends HBox {

    public ManageBox() {
        super();
        init();
    }

    public ManageBox(double spacing) {
        super(spacing);
        init();
    }

    public ManageBox(Node... children) {
        super(children);
        init();
    }

    public ManageBox(double spacing, Node... children) {
        super(spacing, children);
        init();
    }

    private void init() {
        setId("ManageBox");
        setSpacing(5);
        setPadding(new Insets(5));
        setAlignment(Pos.CENTER_LEFT);
    }


    public void setActions(MBB... buttons) {
        getChildren().clear();
        for (MBB button : buttons) {
            getChildren().add(button.create());
        }
    }

    public void setActions(List<Button> buttons) {
        getChildren().setAll(buttons);
    }

    public static class MBB extends ObjectTitle<EventHandler<ActionEvent>> {
        private final FIcon icon;

        public MBB(String title, FIcon icon, EventHandler<ActionEvent> actionEventEventHandler) {
            super(title, actionEventEventHandler);
            this.icon = icon;
        }

        public MBB(String title, EventHandler<ActionEvent> actionEventEventHandler) {
            this(title, null, actionEventEventHandler);
        }

        public <E> MBB(String title, FIcon icon, TableView<E> tableView, Consumer<E> consumer) {
            this(title, icon, d -> ZValidate.do_if_selected(tableView.getSelectionModel(), tableView, consumer));
        }

        private Button create() {
            Button butt = new Button(getTitle(), icon == null ? null : icon.mk(Color.WHITE));
            butt.setOnAction(get());
            return butt;
        }
    }
}
