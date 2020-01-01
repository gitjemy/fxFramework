package com.jemylibs.uilib.utilities.stringfilter;

public interface Filterer<T> {
    boolean isIn(String text, T item);

}
