package com.jemylibs.uilib.ctrls.tables.customCols;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.function.Consumer;
import java.util.function.Function;

public class ButtonCol<Item> extends col<Item, String> {
    private final Consumer<Item> onClick;
    private Function<Item, Boolean> showButton = e -> true;
    private Function<Item, String> getButtonText;

    public ButtonCol(String title, String btn_text, String style_class, Consumer<Item> ZGAction) {
        super(title);
        this.onClick = ZGAction;
        getButtonText = d -> btn_text;
        setCellFactory(tc -> {
            TableCell cell = new TableCell<Item, Object>() {
                Button button = new Button();

                {
                    if (style_class != null) {
                        button.getStyleClass().add(style_class);
                    }
                    setText(null);
                    button.setOnAction(event -> doAction(getCurrentItem()));
                }

                public Item getCurrentItem() {
                    return (Item) getTableView().getItems().get(getIndex());
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
                                button.setText(getButtonText.apply(currentItem));
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

        setSortable(false);
    }

    public ButtonCol(String btn_text, Consumer<Item> ZGAction) {
        this(btn_text, btn_text, null, ZGAction);
    }

    public ButtonCol(String title, String btn_text, Consumer<Item> ZGAction) {
        this(title, btn_text, null, ZGAction);
    }

    public ButtonCol(String btn_text, Consumer<Item> ZGAction, double size) {
        this(btn_text, btn_text, null, ZGAction);
        setPrefWidth(size);
    }

    public void doAction(Item item) {
        if (onClick != null) {
            onClick.accept(item);
        }
    }

    public void setShowButton(Function<Item, Boolean> showButton) {
        this.showButton = showButton;
    }

    public void setGetButtonText(Function<Item, String> getButtonText) {
        this.getButtonText = getButtonText;
    }
}
