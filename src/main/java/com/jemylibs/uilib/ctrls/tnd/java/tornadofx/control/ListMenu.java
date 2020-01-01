package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control;

import com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control.skin.ListMenuSkin;

import java.util.List;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.css.CssMetaData;
import javafx.css.PseudoClass;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.layout.Region;

@DefaultProperty("children")
public class ListMenu extends Control {
    private static final PseudoClass ACTIVE_PSEUDOCLASS_STATE =
            PseudoClass.getPseudoClass("active");

    private static final StyleablePropertyFactory<ListMenu> FACTORY = new StyleablePropertyFactory<>(Region.getClassCssMetaData());
    private final ObjectProperty<Orientation> orientation = new SimpleObjectProperty<Orientation>(Orientation.VERTICAL) {
        protected void invalidated() {
            setNeedsLayout(true);
            requestLayout();
        }
    };
    private final ObjectProperty<Side> iconPosition = new SimpleObjectProperty<Side>(Side.LEFT) {
        protected void invalidated() {
            getChildren().forEach(child -> {
                if (child instanceof ListItem) {
                    ((ListItem) child).needsLayout();
                }
            });
        }
    };
    private final StyleableProperty<Number> graphicFixedSize = FACTORY.createStyleableNumberProperty(this, "graphicFixedSize", "-fx-graphic-fixed-size", ListMenu::graphicFixedSizeProperty);
    private Property<ListItem> active = new SimpleObjectProperty<ListItem>(this, "active") {
        public void set(ListItem item) {
            ListItem previouslyActive = get();
            if (item == previouslyActive)
                return;

            if (previouslyActive != null)
                previouslyActive.pseudoClassStateChanged(ACTIVE_PSEUDOCLASS_STATE, false);

            item.pseudoClassStateChanged(ACTIVE_PSEUDOCLASS_STATE, true);
            super.set(item);
        }
    };

    public ListMenu() {
        getStyleClass().add("list-menu");
        setFocusTraversable(true);
    }

    public ListMenu(ListItem... items) {
        this();
        if (items != null)
            getChildren().addAll(items);
    }

    public final StyleableProperty<Number> graphicFixedSizeProperty() {
        return graphicFixedSize;
    }

    public ListItem getActive() {
        return active.getValue();
    }

    public void setActive(ListItem item) {
        active.setValue(item);
    }

    public Property<ListItem> activeProperty() {
        return active;
    }

    public Orientation getOrientation() {
        return orientation.get();
    }

    public void setOrientation(Orientation orientation) {
        this.orientation.set(orientation);
    }

    public ObjectProperty<Orientation> orientationProperty() {
        return orientation;
    }

    public Side getIconPosition() {
        return iconPosition.get();
    }

    public void setIconPosition(Side iconPosition) {
        this.iconPosition.set(iconPosition);
    }

    public ObjectProperty<Side> iconPositionProperty() {
        return iconPosition;
    }

    protected List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return FACTORY.getCssMetaData();
    }

    protected Skin<?> createDefaultSkin() {
        return new ListMenuSkin(this);
    }

    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }

    public String getUserAgentStylesheet() {
        return ListMenu.class.getResource("listmenu.css").toExternalForm();
    }
}
