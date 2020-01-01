package com.jemylibs.gdb.Query.ZQ;

import com.jemylibs.gdb.ZCOL.COL;

import java.util.ArrayList;

public class Selector {
    private final ArrayList<Condition> conditions;
    private final ArrayList<String> combine_Operators = new ArrayList();
    private String limits = "";
    private String orderStatement = "";

    public Selector(Condition cond) {
        this.conditions = new ArrayList();
        conditions.add(cond);
    }

    public Selector() {
        this.conditions = new ArrayList();
    }

    public Selector(boolean combined, Condition... conditions) {
        this(conditions[0]);
        if (combined) {
            for (int i = 1; i < conditions.length; i++) {
                and(conditions[i]);
            }
        } else {
            for (int i = 1; i < conditions.length; i++) {
                or(conditions[i]);
            }
        }
    }

    public boolean hasValues() {
        return !conditions.isEmpty();
    }

    public String get() {
        String pieces = "";
        if (hasValues()) {
            int size = conditions.size();
            for (int i = 0; i < size; i++) {
                String wherePiece = conditions.get(i).getWherePiece();
                if (wherePiece != null) {
                    pieces += wherePiece;
                    if (i < size - 1) {
                        pieces += combine_Operators.get(i);
                    }
                }
            }
        }
        pieces = (pieces.isEmpty()) ? "" : " where " + pieces;
        pieces += " " + orderStatement + " " + limits;
        return pieces;
    }

    public Selector and(Condition condition) {
        conditions.add(condition);
        combine_Operators.add(" and ");
        return this;
    }

    public Selector or(Condition condition) {
        conditions.add(condition);
        combine_Operators.add(" or ");
        return this;
    }

    public Selector setLimits(int offset, int limit) {
        this.limits = "Limit " + offset + "," + limit;
        return this;
    }

    public Selector orderBy(COL Col) {
        this.orderStatement = "order by " + Col.name;
        return this;
    }

    public void clearLimits() {
        this.limits = "";
    }

    public Selector orderDescBy(COL Col) {
        this.orderStatement = "order by " + Col.name + " desc";
        return this;
    }
}

