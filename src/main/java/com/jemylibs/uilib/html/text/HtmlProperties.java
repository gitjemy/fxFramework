package com.jemylibs.uilib.html.text;

import com.jemylibs.data.seimpl.utility.ObjectTitle;
import com.jemylibs.uilib.html.base.zhtmlComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class HtmlProperties extends zhtmlComponent {
    ArrayList<ObjectTitle<String>> objectTitles;

    public HtmlProperties(String title, ObjectTitle<String>... properties) {
        super(title);
        objectTitles = new ArrayList<>(Arrays.asList(properties));
    }

    @Override
    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<div class='w3-padding-16'><table class='w3-table-all'>");
        builder.append("<tbody>");

        for (ObjectTitle<String> col : objectTitles) {
            builder.append("<tr>");
            builder.append(createCell(col.getTitle()));
            builder.append(createCell(col.get()));
            builder.append("</tr>");
        }

        builder.append("</tbody>");
        builder.append("</table></div>");
        return builder.toString();
    }

    private String createCell(String content) {
        return "<td>" + content + "</td>";
    }
}
