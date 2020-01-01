package com.jemylibs.uilib.utilities;

import java.util.Objects;

public interface ConsumerWithException<T> {

    void accept(T t) throws Exception;

    default ConsumerWithException<T> andThen(ConsumerWithException<? super T> var1) {
        Objects.requireNonNull(var1);
        return (var2) -> {
            this.accept(var2);
            var1.accept(var2);
        };
    }
}
