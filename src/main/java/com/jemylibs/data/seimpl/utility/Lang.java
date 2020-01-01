package com.jemylibs.data.seimpl.utility;

import java.util.ArrayList;
import java.util.Random;

public class Lang {

    private static Random random = new Random();

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static public boolean IsNumber(Object Item) {
        try {
            Double.valueOf(Item.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static <X> X random_item(X... xes) {
        return xes[random.nextInt(xes.length)];
    }

    public static <X> X random_item(ArrayList<X> xes) {
        return xes.get(random.nextInt(xes.size()));
    }
}
