package com.jemylibs.uilib.html.text;

import com.jemylibs.uilib.html.base.zhtmlItem;

public class HtmlDiv implements zhtmlItem {
    private final String classes;
    String content;

    public HtmlDiv(String text, String classes) {
        content = text;
        this.classes = classes;
    }

    public HtmlDiv(String text) {
        this(text, "w3-center");
    }

    @Override
    public String getHtml() {
        return "<div " + getclass() + " >" + content + "</div>";
    }

    private String getclass() {
        return "class='" + classes + "'";
    }
}
