package com.jemylibs.uilib.html.table;

public class IndexCol<E> extends HtmlCol<E> {

    public IndexCol() {
        super("#", null);
    }

    @Override
    public String getCellContent(int index, E e) {
        return index + "";
    }
}
