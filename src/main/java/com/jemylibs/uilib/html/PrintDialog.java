package com.jemylibs.uilib.html;

import com.jemylibs.data.seimpl.utility.IO;
import com.jemylibs.uilib.ZView.containers.Dialog;
import com.jemylibs.uilib.html.base.ZHtmlPage;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.windows.ShowFilePath;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class PrintDialog extends Dialog {

    private HtmlComponentsSelector selector;
    private WebView webView = new WebView();

    private Button close = new Button("إغلاق");
    private Button back = new Button("رجوع");
    private Button edit = new Button("تم التعديل");
    private Button print = new Button("طباعة");
    private Button saveHtml = new Button("تسجيل الصفحة");

    private StackPane pane = new StackPane();

    public PrintDialog(final ZHtmlPage page) {
        super("printing ...");
        this.selector = HtmlComponentsSelector.getInstance(page);

        edit.setOnAction(s -> {
            pane.getChildren().setAll(webView);
            setButtons(saveHtml, print, back, close);
            webView.getEngine().loadContent(selector.getPage().getHtml());
        });
        close.setOnAction(e -> PrintDialog.this.close());

        back.setOnAction(e -> {
            setButtons(edit, close);
            pane.getChildren().setAll(selector.getParent());
        });

        setButtons(edit, close);

        print.setOnAction(e -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(webView.getScene().getWindow())) {
                webView.getEngine().print(job);
                job.endJob();
            }
        });

        saveHtml.setOnAction(d -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("مسار الملف");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("ملفات بلغة (*.html)", "*.html");
            chooser.getExtensionFilters().add(extFilter);
            File file = chooser.showSaveDialog(getStage().getScene().getWindow());
            if (file != null) {
                try {
                    IO.writeFile(file, page.getHtml());
                    new ShowFilePath(chooser.getTitle(), file);
                } catch (IOException e) {
                    ZAlert.errorHandle(e);
                }

            }
        });

        pane.getChildren().setAll(selector.getParent());
    }

    @Override
    protected void beforeShow() {
        this.getStage().setWidth(1000);
        this.getStage().setHeight(750);
    }

    @Override
    protected Parent getTheView() {
        return pane;
    }
}
