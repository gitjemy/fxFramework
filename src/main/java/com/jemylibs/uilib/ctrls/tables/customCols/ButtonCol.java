package com.jemylibs.uilib.ctrls.tables.customCols;

import com.jemylibs.uilib.utilities.icon.fontIconLib.Icon;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ButtonCol<Item> extends col<Item, String> {

    private final Consumer<Item> onClick;
    private Function<Item, Boolean> showButton = e -> true;
    private Function<Item, String> getButtonText;
    private int buttonSize = -1;
    private String baseColor;

    {
        setSortable(false);
        getStyleClass().add("button-col");
    }

    public ButtonCol(String title, Supplier<Icon> btn_icn, Consumer<Item> ZGAction) {
        this(title, (Function<Item, String>) null, btn_icn, ZGAction);
    }

    public ButtonCol(String title, String txt, Consumer<Item> ZGAction) {
        this(title, item -> txt, null, ZGAction);
    }

    public ButtonCol(String title, String txt, Supplier<Icon> btn_icn, Consumer<Item> ZGAction) {
        this(title, item -> txt, btn_icn, ZGAction);
    }

    public ButtonCol(String title, Function<Item, String> btn_txt, Supplier<Icon> btn_icn, Consumer<Item> ZGAction) {
        super(title);
        this.onClick = ZGAction;
        getButtonText = btn_txt;

        setCellFactory(tc -> {
            TableCell cell = new TableCell<Item, Object>() {
                Button button;

                void create() {
                    button = new Button();
                    button.getStyleClass().add("button-cell");

                    if (btn_icn != null) {
                        button.setGraphic(btn_icn.get());
                        button.getStyleClass().add("icon-button-cell");
                    }

                    if (btn_txt != null) {
                        button.getStyleClass().add("text-button-cell");
                    }

                    button.setOnAction(event -> doAction(getCurrentItem()));
                    button.setStyle((buttonSize != -1 ? "-fx-size : " + buttonSize + "px;" : "")
                            + ((baseColor != null) ? " -fx-base:" + baseColor + " !important;" : ""));

                    onNewButtonCreated(button);
                    setText(null);
                }

                public Item getCurrentItem() {
                    return getTableView().getItems().get(getIndex());
                }

                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Item currentItem = getCurrentItem();
                        if (currentItem == null) {
                            setGraphic(null);
                        } else {
                            if (showButton.apply(currentItem)) {
                                if (button == null) create();
                                if (getButtonText != null) button.setText(getButtonText.apply(currentItem));
                                setGraphic(button);
                            } else {
                                setGraphic(null);
                            }
                        }
                    } else {
                        setGraphic(null);
                    }
                }
            };
            cell.setWrapText(true);
            return cell;
        });
    }

    public void onNewButtonCreated(Button button) {

    }

    public void doAction(Item item) {
        if (onClick != null) {
            onClick.accept(item);
        }
    }

    public ButtonCol<Item> setStyle(int buttonSize, String baseColor) {
        this.buttonSize = buttonSize;
        this.baseColor = baseColor;
        return this;
    }

    public void setShowButton(Function<Item, Boolean> showButton) {
        this.showButton = showButton;
    }

    public void setGetButtonText(Function<Item, String> getButtonText) {
        this.getButtonText = getButtonText;
    }
}
