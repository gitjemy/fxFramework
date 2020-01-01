package com.jemylibs.uilib.html.base;

import com.jemylibs.data.seimpl.utility.IO;

public class ZHtmlPage extends zhtmlComponent {

    public ZHtmlPage(zhtmlComponent... items) {
        super("الصفحة", items);
    }

    @Override
    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html lang='ar'>");
        builder.append("<head>");
        builder.append("<style>");
        appendStyle(builder);
        builder.append("</style>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append(super.getHtml());
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    public void appendStyle(StringBuilder builder) {
        builder.append(IO.ReadFileFromRecourse("/html/css/w3.css", ZHtmlPage.class));
        builder.append(IO.ReadFileFromRecourse("/html/css/ovvw3.css", ZHtmlPage.class));
    }
}
