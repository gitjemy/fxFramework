package com.jemylibs.uilib.utilities.stringfilter;

import com.jemylibs.uilib.ctrls.autocompelete.AutoCompleteItem;

import java.util.function.Predicate;

public interface SimpleFilterer<T> extends Filterer<AutoCompleteItem<T>> {
    String searchIn(T item);

    @Override
    default boolean isIn(String text, AutoCompleteItem<T> item) {
        String s = searchIn(item.get());
        return s.toLowerCase().contains(text);
    }

    default Predicate<T> equalP(String search) {
        return t -> searchIn(t).equals(search);
    }

}
