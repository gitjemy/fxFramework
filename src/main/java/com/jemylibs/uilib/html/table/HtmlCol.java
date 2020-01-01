package com.jemylibs.uilib.html.table;

import com.jemylibs.uilib.html.base.zhtmlComponent;

import java.util.function.Function;

public class HtmlCol<E> extends zhtmlComponent {

    private Function<E, ?> reader;

    public HtmlCol(String title, Function<E, ?> reader) {
        super(title);
        this.reader = reader;
    }

    public String getCellContent(int index, E e) {
        return reader.apply(e) + "";
    }


    public String createHeaderCell() {
        return "<td>" + getTitle() + "</td>";
    }

    @Override
    public String toString() {
        return "عمود '" + super.getTitle() + "'";
    }
}
