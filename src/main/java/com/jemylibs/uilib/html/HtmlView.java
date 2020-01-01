package com.jemylibs.uilib.html;

import com.jemylibs.uilib.html.base.ZHtmlPage;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class HtmlView extends VBox {
    final private WebView webView = new WebView();
    final private Button printButton = new Button("طباعة...");
    final private Button copyCode = new Button("نسخ الكود");
    private ZHtmlPage htmlpage;

    {
//        HBox buttonBar = new HBox(printButton, copyCode);
        HBox buttonBar = new HBox(printButton);
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(10));

        copyCode.setOnAction(e -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(htmlpage.getHtml());
            clipboard.setContent(clipboardContent);
        });

        printButton.setOnAction(d -> {
//            webView.getEngine().print(PrinterJob.createPrinterJob());
            PrintDialog printDialog = new PrintDialog(this.htmlpage);
            printDialog.showAndWait();
        });

        getChildren().setAll(buttonBar, webView);
        VBox.setVgrow(webView, Priority.ALWAYS);
        reload();
    }

    public HtmlView() {
    }

    public HtmlView(double spacing) {
        super(spacing);
    }

    public HtmlView(Node... children) {
        super(children);
    }

    public HtmlView(double spacing, Node... children) {
        super(spacing, children);
    }

    public ZHtmlPage getHtmlpage() {
        return htmlpage;
    }

    public void setHtmlpage(ZHtmlPage htmlpage) {
        this.htmlpage = htmlpage;
        reload();
    }

    public void reload() {
        if (htmlpage == null) {
            webView.getEngine().loadContent("");
        } else {
            webView.getEngine().loadContent(htmlpage.getHtml());
        }
    }
}
