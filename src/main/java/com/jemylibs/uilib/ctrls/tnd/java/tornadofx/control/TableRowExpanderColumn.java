package com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control;

import com.jemylibs.uilib.ctrls.tnd.java.tornadofx.control.skin.ExpandableTableRowSkin;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * The TableRowExpanderColumn enables a TableView to provide an expandable editor below each table row.
 * The column itself contains a toggle button that on click will show an editor for the current row right below the
 * columns. Example:
 *
 * <pre>
 * TableRowExpanderColumn&lt;Customer> expander = new TableRowExpanderColumn&lt;>(param -> {
 *     HBox editor = new HBox(10);
 *     TextField text = new TextField(param.getValue().getName());
 *     Button save = new Button("Save customer");
 *     save.setOnAction(event -> {
 *         save();
 *         param.toggleExpanded();
 *     });
 *     editor.getChildren().addAll(text, save);
 *     return editor;
 * });
 *
 * tableView.getColumns().add(expander);
 * </pre>
 * <p>
 * You can provide a custom cellFactory to customize the toggle button. A typical custom toggle cell implementation
 * would look like this:
 *
 * <pre>
 * public class MyCustomToggleCell&lt;S> extends TableCell&lt;S, Boolean> {
 *     private Button button = new Button();
 *
 *     public MyCustomToggleCell(TableRowExpanderColumn&lt;S> column) {
 *         button.setOnAction(event -> column.toggleExpanded(getIndex()));
 *     }
 *
 *     protected void updateItem(Boolean expanded, boolean empty) {
 *         super.updateItem(expanded, empty);
 *         if (expanded == null || empty) {
 *             setGraphic(null);
 *         } else {
 *             button.setText(expanded ? "Collapse" : "Expand");
 *             setGraphic(button);
 *         }
 *     }
 * }
 * </pre>
 * <p>
 * The custom toggle cell utilizes the {@link TableRowExpanderColumn#toggleExpanded(int)} method to toggle
 * the row expander.
 *
 * @param <S> The item type of the TableView
 */
public final class TableRowExpanderColumn<S> extends TableColumn<S, Boolean> {
    private static final String STYLE_CLASS = "expander-column";
    private static final String EXPANDER_BUTTON_STYLE_CLASS = "expander-button";

    private final Map<S, Node> expandedNodeCache = new HashMap<>();
    private final Map<S, SimpleBooleanProperty> expansionState = new HashMap<>();
    private Callback<TableRowDataFeatures<S>, Node> expandedNodeCallback;

    public TableRowExpanderColumn(TableView<S> tableView, Callback<TableRowDataFeatures<S>, Node> expandedNodeCallback) {
        this(expandedNodeCallback);
        tableView.getColumns().add(this);
    }

    /**
     * Create a row expander column that can be added to the TableView list of columns.
     * <p>
     * The expandedNodeCallback is expected to return a Node representing the editor that should appear below the
     * table row when the toggle button within the expander column is clicked.
     * <p>
     * Once this column is assigned to a TableView, it will automatically install a custom row factory for the TableView
     * so that it can configure a TableRow with the {@link ExpandableTableRowSkin}. It is within the skin that the actual
     * rendering of the expanded node occurs.
     *
     * @param expandedNodeCallback
     * @see TableRowExpanderColumn
     * @see TableRowDataFeatures
     */
    public TableRowExpanderColumn(Callback<TableRowDataFeatures<S>, Node> expandedNodeCallback) {
        this.expandedNodeCallback = expandedNodeCallback;

        getStyleClass().add(STYLE_CLASS);
        setCellValueFactory(param -> getExpandedProperty(param.getValue()));
        setCellFactory(param -> new ToggleCell());
        installRowFactoryOnTableViewAssignment();
    }

    /**
     * Returns a Boolean property that can be used to manipulate the expanded state for a row
     * corresponding to the given item value.
     *
     * @param item The item corresponding to a table row
     * @return The boolean property
     */
    public SimpleBooleanProperty getExpandedProperty(S item) {
        SimpleBooleanProperty value = expansionState.get(item);
        if (value == null) {
            value = new SimpleBooleanProperty(item, "expanded", false) {
                /**
                 * When the expanded state change we refresh the tableview.
                 * If the expanded state changes to false we remove the cached expanded node.
                 */
                @Override
                protected void invalidated() {
                    getTableView().refresh();
                    if (!getValue()) expandedNodeCache.remove(getBean());
                }
            };
            expansionState.put(item, value);
        }
        return value;
    }

    /**
     * Get or create and cache the expanded node for a given item.
     *
     * @param tableRow The table row, used to find the item index
     * @return The expanded node for the given item
     */
    public Node getOrCreateExpandedNode(TableRow<S> tableRow) {
        int index = tableRow.getIndex();
        if (index > -1 && index < getTableView().getItems().size()) {
            S item = getTableView().getItems().get(index);
            Node node = expandedNodeCache.get(item);
            if (node == null) {
                node = expandedNodeCallback.call(new TableRowDataFeatures<>(tableRow, this, item));
                expandedNodeCache.put(item, node);
            }
            return node;
        }
        return null;
    }

    /**
     * Return the expanded node for the given item, if it exists.
     *
     * @param item The item corresponding to a table row
     * @return The expanded node, if it exists.
     */
    public Node getExpandedNode(S item) {
        return expandedNodeCache.get(item);
    }

    /**
     * Install the row factory on the TableView when this column is assigned to a TableView.
     */
    private void installRowFactoryOnTableViewAssignment() {
        tableViewProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getTableView().setRowFactory(param -> new TableRow<S>() {
                    @Override
                    protected Skin<?> createDefaultSkin() {
                        return new ExpandableTableRowSkin<>(this, TableRowExpanderColumn.this);
                    }
                });
            }
        });
    }

    /**
     * Toggle the expanded state of the row at the given index.
     *
     * @param index The index of the row you want to toggle expansion for.
     */
    public void toggleExpanded(int index) {
        SimpleBooleanProperty expanded = (SimpleBooleanProperty) getCellObservableValue(index);
        expanded.setValue(!expanded.getValue());
    }

    /**
     * This object is passed to the expanded node callback when it is time to create a Node to represent the
     * expanded editor of a certain row. The most important method is {@link #getValue()}} which returns the
     * object represented by the current row.
     * <p>
     * Further more, the {@link #expandedProperty()} returns a boolean property indicating the current expansion
     * state of the current row. You can use this, or the {@link #toggleExpanded()} method to toggle and inspect
     * the expanded state of the row, for example if you want an action inside the row editor to contract the editor.
     *
     * @param <S> The type of items in the TableView
     */
    public static class TableRowDataFeatures<S> {
        private TableRow<S> tableRow;
        private TableRowExpanderColumn<S> tableColumn;
        private SimpleBooleanProperty expandedProperty;
        private S value;

        public TableRowDataFeatures(TableRow<S> tableRow, TableRowExpanderColumn<S> tableColumn, S value) {
            this.tableRow = tableRow;
            this.tableColumn = tableColumn;
            this.expandedProperty = (SimpleBooleanProperty) tableColumn.getCellObservableValue(tableRow.getIndex());
            this.value = value;
        }

        /**
         * Return the current TableRow. It is safe to assume that the index returned by {@link TableRow#getIndex()} is
         * correct as long as you use it for the initial node creation. It is not safe to trust the result of this call
         * at any later time, for example in a button ZGAction within the row editor.
         *
         * @return The current TableRow
         */
        public TableRow<S> getTableRow() {
            return tableRow;
        }

        /**
         * Return the TableColumn which contains the toggle button. Normally you would not need to use this directly,
         * but rather consult the {@link #expandedProperty()} for inspection and mutation of the toggled state of this row.
         *
         * @return The TableColumn which contains the toggle button
         */
        public TableRowExpanderColumn<S> getTableColumn() {
            return tableColumn;
        }

        /**
         * The expanded property can be used to inspect or mutate the toggled state of this row editor. You can also
         * listen for changes to it's state if needed.
         *
         * @return The expanded property
         */
        public SimpleBooleanProperty expandedProperty() {
            return expandedProperty;
        }

        /**
         * Toggle the expanded state of this row editor.
         */
        public void toggleExpanded() {
            SimpleBooleanProperty expanded = expandedProperty();
            expanded.setValue(!expanded.getValue());
        }

        /**
         * Returns a boolean indicating if the current row is expanded or not
         *
         * @return A boolean indicating the expanded state of the current editor
         */
        public Boolean isExpanded() {
            return expandedProperty().getValue();
        }

        /**
         * Set the expanded state. This will update the {@link #expandedProperty()} accordingly.
         *
         * @param expanded Wheter the row editor should be expanded or not
         */
        public void setExpanded(Boolean expanded) {
            expandedProperty().setValue(expanded);
        }

        /**
         * The value represented by the current table row. It is important that the value has valid equals/hashCode
         * methods, as the row value is used to keep track of the node editor for each row.
         *
         * @return The value represented by the current table row
         */
        public S getValue() {
            return value;
        }

    }

    /**
     * The default toggle cell creates a button with a + or - sign as the text,
     * depending on the expanded state of the row it represents.
     * <p>
     * You can use this as a starting point to implement a custom toggle cell.
     */
    private final class ToggleCell extends TableCell<S, Boolean> {
        private Button button = new Button();

        public ToggleCell() {
            button.setFocusTraversable(false);
            button.getStyleClass().add(EXPANDER_BUTTON_STYLE_CLASS);
            button.setPrefSize(16, 16);
            button.setPadding(new Insets(0));
            button.setOnAction(event -> toggleExpanded(getIndex()));
        }

        @Override
        protected void updateItem(Boolean expanded, boolean empty) {
            super.updateItem(expanded, empty);
            if (expanded == null || empty) {
                setGraphic(null);
            } else {
                button.setText(expanded ? "-" : "+");
                setGraphic(button);
            }
        }
    }

}
