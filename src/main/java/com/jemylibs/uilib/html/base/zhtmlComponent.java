package com.jemylibs.uilib.html.base;

import java.util.ArrayList;
import java.util.Arrays;

public class zhtmlComponent extends ArrayList<zhtmlItem> implements zhtmlItem {
    private final String title;
    private boolean hidden = false;

    public zhtmlComponent(String title, zhtmlItem... items) {
        this.title = title;
        this.addAll(Arrays.asList(items));
    }

    @Override
    public String getHtml() {
        if (!isHidden()) {
            StringBuilder builder = new StringBuilder();
            builder.append("<div>");
            forEach(zhtmlItem -> {
                if (!isHidden()) {
                    builder.append(zhtmlItem.getHtml());
                }
            });
            builder.append("</div>");
            return builder.toString();
        } else {
            return "";
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
