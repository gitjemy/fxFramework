package com.jemylibs.uilib.utilities.stringfilter;

import java.util.function.Function;
import java.util.function.Predicate;

public class StringEqualPredicate<E> implements Predicate<E> {

    final private Function<E, String> searchIn;
    final private String search;


    public StringEqualPredicate(Function<E, String> searchIn, String search) {
        this.searchIn = searchIn;
        this.search = search;
    }


    @Override
    public boolean test(E o) {
        return searchIn.apply(o).equals(search);
    }
}
