package com.jemylibs.uilib.ctrls.inputs;

import com.jemylibs.uilib.Application;
import com.jemylibs.uilib.ctrls.ManageBox;
import com.jemylibs.uilib.ctrls.tables.Tables;
import com.jemylibs.uilib.ctrls.tables.customCols.ButtonCol;
import com.jemylibs.uilib.ctrls.tables.customCols.IndexCol;
import com.jemylibs.uilib.ctrls.tables.customCols.PropertyCol;
import com.jemylibs.uilib.ctrls.tables.customCols.RemoveButtonCol;
import com.jemylibs.uilib.utilities.alert.Dialog;
import com.jemylibs.uilib.utilities.alert.ZAlert;
import com.jemylibs.uilib.utilities.icon.fontIconLib.support.FontAwesome;
import com.jemylibs.uilib.view.templates.EntityTable.TableManager;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DbFolderFiles extends StackPane {
    private final File dir;
    TableManager<File> tableManager = TableManager.create();

    public DbFolderFiles(File dir) {
        this.dir = dir;
        TableView<File> table = tableManager.getTable();
        PropertyCol<File, String> col = new PropertyCol<>("إسم الملف", "name");

        Tables.init_table(table,
                new IndexCol<>(),
                new RemoveButtonCol<>(file -> {
                    if (file.exists() && Dialog.ConfirmationAlert("تأكيد", "سوف يتم حذف الملف " + file.getName() + "\n"
                            + "هل أنت متأكد")) {
                        file.delete();
                    }
                    refresh();
                }), new ButtonCol<>("فتح اللف", FontAwesome.FA_FLASH, e -> {
                    try {
                        Desktop.getDesktop().open(e);
                    } catch (IOException ex) {
                        ZAlert.errorHandle(ex);
                    }
                }),
                new ButtonCol<>("فتح الحافظة", FontAwesome.FA_FOLDER_OPEN, e -> {
                    try {
                        Desktop.getDesktop().open(dir);
                    } catch (IOException ex) {
                        ZAlert.errorHandle(ex);
                    }
                }),
                col
        );

        col.setCellFactory(param -> new TableCell<File, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Image fxImage = FileSystemUtility.getFileIcon(item);
                    ImageView imageView = new ImageView(fxImage);
                    setGraphic(imageView);
                    setText(item);
                }
            }
        });
        getChildren().add(tableManager.getParent());


        tableManager.getManage_box().setActions(new ManageBox.MBB(Application.getResourceString("Add"), event -> {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select File ...");

            File file = chooser.showOpenDialog(getScene().getWindow());

            if (file != null) {
                try {
                    File file1 = new File(dir.getAbsolutePath() + "/" + file.getName());

                    if (!file1.exists()) {
                        Files.copy(file.toPath(), file1.toPath());
                    } else if (Dialog.WarningAlert("إنتبه", "هذا الملف موجود من قبل وسوف يتم إستبداله بالملف الجديد" + "\n" +
                            "هل انت متأكد أنك تريد الاستبدال")) {
                        Files.copy(file.toPath(), file1.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                    refresh();
                } catch (IOException e) {
                    ZAlert.errorHandle(e);
                }
            }
        }));
        refresh();
    }

    private void refresh() {
        File[] files = dir.listFiles();
        tableManager.getTable().getItems().setAll(files);
    }
}
