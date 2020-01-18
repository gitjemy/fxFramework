package com.jemylibs.gdb.properties;


import com.jemylibs.gdb.utility.StringUtils;

import java.math.BigDecimal;

public class WritableProperty<E, V> extends Property<E, V> {

    public final Writer<E, V> writer;

    public WritableProperty(String title, Func<E, V> reader, Writer<E, V> writer) {
        super(title, reader);
        this.writer = writer;
    }

    public WritableProperty(final String name, String title) {
        super(title, new Func<E, V>() {
            @Override
            public V apply(E var1) {
                try {
                    String methodName = "get" + StringUtils.capitalize(name);
                    Object invoke = var1.getClass().getMethod(methodName).invoke(var1);
                    return (V) invoke;
                } catch (Exception var4) {
                    return null;
                }
            }
        });
        this.writer = new Writer<E, V>() {
            @Override
            public void set(E o, V o2) {
                try {
                    String methodName = "set" + StringUtils.capitalize(name);
                    o.getClass().getMethod(methodName, o2.getClass()).invoke(o, o2);
                } catch (Exception var4) {
                }

            }
        };
    }

    public void setValue(E e, V v) {
        writer.set(e, v);
    }

    public void setValueFromString(E e, String token) throws Exception {
        if (token == null) return;

        boolean written = false;
        try {
            writer.set(e, token == null ? (V) "" : (V) token);
            written = true;
        } catch (Exception ignored) {

        }

        if (!written) {
            try {
                BigDecimal decimal = new BigDecimal(token.trim());
                try {
                    writer.set(e, (V) decimal);
                    written = true;
                } catch (Exception ignored) {
                }
                if (!written) {
                    try {
                        writer.set(e, (V) new Integer(decimal.intValue()));
                        written = true;
                    } catch (Exception ignored) {
                    }
                }
            } catch (Exception ignored) {
            }
        }
        if (!written) {
            throw new Exception("لا يمكن تسجيل تحويل القيمة" + "\n" + token + "\n" + getTitle());
        }
    }


    public void validate(V v) throws Exception {

    }
}
