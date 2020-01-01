package com.jemylibs.uilib.html.table;

import com.jemylibs.uilib.html.base.zhtmlComponent;
import com.jemylibs.uilib.html.base.zhtmlItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;

public class HtmlTable<E> extends zhtmlComponent {

    private List<E> rows;

    public HtmlTable(String title, HtmlCol<E>... cols) {
        super(title);
        addAll(Arrays.asList(cols));
    }

    public static <X> HtmlTable<X> fromList(String title, List<X> list, HtmlCol<X>... cols) {
        HtmlTable<X> zhtmlItems = new HtmlTable<>(title, cols);
        zhtmlItems.setRows(new ArrayList<>(list));
        return zhtmlItems;
    }

    public static <E> HtmlTable<E> fromTableView(String title, TableView<E> tableView) {
        HtmlTable<E> zhtmlItems = new HtmlTable<>(title, fromTableViewCols(tableView.getColumns()));
        zhtmlItems.setRows(new ArrayList<>(tableView.getItems()));
        return zhtmlItems;
    }

    @SuppressWarnings("unchecked")
    public static <E, x extends TableColumn<E, ?>> HtmlCol<E>[] fromTableViewCols(List<x> columns) {
        return columns.stream()
                .filter(TableColumnBase::isVisible)
                .map(HtmlTable::fromTableViewCols)
                .toArray(HtmlCol[]::new);
    }

    private static <E, x extends TableColumn<E, ?>> HtmlCol<E> fromTableViewCols(x column) {
        return new HtmlCol<>(column.getText(), s -> column.getCellData(s));
    }

    public void setRows(ArrayList<E> rows) {
        this.rows = rows;
    }

    @Override
    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div class='table_container'><table class=''>");
        builder.append("<thead>");
        builder.append("<tr>");

        for (zhtmlItem item : this) {
            if (item instanceof HtmlCol && !((HtmlCol) item).isHidden()) {
                builder.append(((HtmlCol) item).createHeaderCell());
            }
        }

        builder.append("</tr>");
        builder.append("</thead>");

        builder.append("<tbody>");
        List<E> rows1 = this.rows;
        for (int i = 0; i < rows1.size(); i++) {
            E row = rows1.get(i);
            builder.append("<tr>");
            for (zhtmlItem item : this) {
                if (item instanceof HtmlCol && !((HtmlCol) item).isHidden()) {
                    builder.append("<td>").append(((HtmlCol) item).getCellContent(i, row)).append("</td>");
                }
            }
            builder.append("</tr>");
        }
        builder.append("</tbody>");

        builder.append("</table></div>");
        return builder.toString();
    }

    @Override
    public String toString() {
        return "جدول " + super.getTitle();
    }
}
