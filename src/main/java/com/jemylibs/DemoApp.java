package com.jemylibs;

import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.ZView.containers.ZTb;
import com.jemylibs.uilib.ctrls.ZMenu;
import com.jemylibs.uilib.ctrls.tables.Tables;
import com.jemylibs.uilib.ctrls.tables.customCols.IndexCol;
import com.jemylibs.uilib.ctrls.tables.customCols.MethodCol;
import com.jemylibs.uilib.ctrls.tables.customCols.PropertyCol;
import com.jemylibs.uilib.ctrls.tables.customCols.RemoveButtonCol;
import com.jemylibs.uilib.utilities.icon.fontIconLib.IconBuilder;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import com.jemylibs.uilib.windows.MainView;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Locale;

public class DemoApp extends Application {

    private MainView mainView;

    public DemoApp() {
        super(Locale.forLanguageTag("ar"));
    }

    @Override
    protected void configureMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    protected void startApp(Stage stage) {
//        mainView.SetTitle("Application Name");
        stage.setMaximized(true);
        stage.show();

        class ssd extends ZTb {

            @Override
            public Parent getView() {
                TableView<FontAwesome> objectTableView = new TableView<>();
                objectTableView.getItems().setAll(FontAwesome.values());

                Tables.init_table(objectTableView,
                        new IndexCol<>(),
                        new RemoveButtonCol<>(),
                        new PropertyCol<>("Font Name", "fontName"),
                        new PropertyCol<>("Char", "char"),
                        new MethodCol<>("name", FontAwesome::toString),
                        new MethodCol<>("To String", FontAwesome::toString)
                );
                return objectTableView;
            }

            @Override
            public void onReload(source reload_source) throws Exception {

            }

            @Override
            public String getTitle() {
                return "Application Matter";
            }
        }
        new ssd().open();

        mainView.addMenu(new Menu("Settings", IconBuilder.menu_bar(FontAwesome.FA_COGS),
                new ZMenu("asd", event -> new ssd().open())));
    }
}
