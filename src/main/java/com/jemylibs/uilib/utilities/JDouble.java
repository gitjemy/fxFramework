package com.jemylibs.uilib.utilities;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;

public class JDouble {


    /**
     * this is the default Scale in the bareza System to be the maximum user can
     * perform
     */
    public final static int maxScale = 1;
    /**
     * this is the default Scale in the bareza System to be the maximum user can
     * perform
     */
    public final static RoundingMode RoundingType = RoundingMode.HALF_UP;
    final private static int UserShowScale = 1;
    /**
     * system currency
     */
    private static final String currency_factory = "V ج";
    private final static String currency_factory_replacement = "V";


    public static <E> BigDecimal sum_list(List<E> list, Function<E, BigDecimal> getter) {
        BigDecimal sum = BigDecimal.ZERO;
        for (E item : list) {
            BigDecimal value = getter.apply(item);
            sum = sum.add(value);
        }
        return sum;
    }

    public static <E> BigDecimal sum_list_debit(List<E> list, Function<E, BigDecimal> getter) {
        BigDecimal sum = BigDecimal.ZERO;
        for (E item : list) {
            BigDecimal value = getter.apply(item);
            if (is_debit(value)) {
                sum = sum.add(value);
            }
        }
        return sum;
    }

    public static <E> BigDecimal sum_list_credit(List<E> list, Function<E, BigDecimal> getter) {
        BigDecimal sum = BigDecimal.ZERO;
        for (E item : list) {
            BigDecimal value = getter.apply(item);
            if (!is_debit(value)) {
                sum = sum.add(value);
            }
        }
        return sum;
    }

    public static String ShowValue(BigDecimal value) {
        value = value.setScale(UserShowScale, RoundingType);
        int signum = value.signum();
        if (signum < 0) {
            return currency_factory.replaceFirst(currency_factory_replacement, value.abs().toPlainString());
        } else {
            return currency_factory.replaceFirst(currency_factory_replacement, value.toPlainString());
        }
    }

    public static String ShowAbsValue(BigDecimal value) {
        value = value.setScale(UserShowScale, RoundingType);
        return currency_factory.replaceFirst(currency_factory_replacement, value.abs().toPlainString());
    }


    public static String showJustDebit(BigDecimal value) {
        value = value.setScale(UserShowScale, RoundingType);
        int signum = value.signum();
        if (signum < 0) {
            return currency_factory.replaceFirst(currency_factory_replacement, value.abs().toPlainString());
        } else {
            return "";
        }
    }

    public static String showJustCredit(BigDecimal value) {
        value = value.setScale(UserShowScale, RoundingType);
        int signum = value.signum();
        if (signum > 0) {
            return currency_factory.replaceFirst(currency_factory_replacement, value.toPlainString());
        } else {
            return "";
        }
    }

    public static boolean is_debit(BigDecimal value) {
        return value.signum() < 0;
    }


    public static BigDecimal formatForDb(BigDecimal val) {
        return val.setScale(maxScale, JDouble.RoundingType);
    }

    public static String showQuantity(int q) {
        return Math.abs(q) + "";
    }

    public static BigDecimal divide(BigDecimal value, int intValue) {
        return divide(value, BigDecimal.valueOf(intValue));
    }

    public static BigDecimal divide(BigDecimal value, BigDecimal on) {
        return value.divide(on, MathContext.DECIMAL32);
    }
}
