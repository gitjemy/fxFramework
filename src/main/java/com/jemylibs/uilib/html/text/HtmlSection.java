package com.jemylibs.uilib.html.text;

import com.jemylibs.uilib.html.base.zhtmlComponent;
import com.jemylibs.uilib.html.base.zhtmlItem;

public class HtmlSection extends zhtmlComponent {

    boolean showTitle;

    public HtmlSection(String title, zhtmlItem... items) {
        super(title, items);
    }

    public HtmlSection(boolean showTitle, String title, zhtmlItem... items) {
        super(title, items);
        this.showTitle = showTitle;
    }

    @Override
    public String getHtml() {
        if (!isHidden()) {
            if (showTitle) {
                String s = "<div class='section'>" +
                        "<div class='section-title w3-header'>" + getTitle() + "</div>" +
                        super.getHtml() +
                        "</div>";
                return s;
            } else {
                return "<div class='section'>" + super.getHtml() + "</div>";
            }
        } else {
            return "";
        }
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }
}
