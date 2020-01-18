package com.jemylibs.data.seimpl.utility;

import com.jemylibs.gdb.properties.Func;
import com.jemylibs.gdb.properties.Property;
import com.jemylibs.sedb.helpers.SQLiteHelper;
import com.jemylibs.sedb.utility.JDateTime;
import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.utilities.ZValidate;
import com.jemylibs.uilib.utilities.alert.Toast;
import com.jemylibs.uilib.windows.MainView;
import com.jemylibs.uilib.windows.ShowFilePath;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IO {

    private static char[] pwd = "XXMMZZ1590X1#$F``'`FSDJKFSDF@@@".toCharArray();

    public static void ImportDate(Node node, Consumer<ArrayList<String[]>> rowsConsumer) throws IOException, InvalidFormatException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx", "*.xls")
        );
        File file = chooser.showOpenDialog(node.getScene().getWindow());
        if (file != null) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            rowsConsumer.accept(getSheetValues(sheet));
        }
    }

    public static ArrayList<String[]> getSheetValues(Sheet sheet) {
        try {
            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();
            Row row;
            Cell cell;
            int cols = 0; // No of columns
            int tmp;
            // This trick ensures that we get the data properly even if it doesn't start from first few rows
            for (int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) cols = tmp;
                }
            }
            ArrayList<String[]> data = new ArrayList<>();
            DataFormatter df = new DataFormatter();

            for (int r = 0; r < rows; r++) {
                row = sheet.getRow(r);
                String[] rowData = new String[cols];
                if (row != null) {
                    for (short c = 0; c < cols; c++) {
                        cell = row.getCell(c);
                        if (cell != null) {
                            rowData[c] = df.formatCellValue(cell);
                        }
                    }
                }
                data.add(rowData);
            }
            return data;
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static File showSaveDialog(String defaultFileName, String title, String... formats) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("excel files (" + String.join(",", formats) + ")", formats)
        );
        chooser.setInitialFileName(defaultFileName);
        return chooser.showSaveDialog(UIController.mainStage);
    }

    public static File showOpenDialog(String title, String... formats) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(title);
        chooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("excel files (" + String.join(",", formats) + ")", formats)
        );
        return chooser.showOpenDialog(UIController.mainStage);
    }

    public static <E> void ExportXlxs(String sheetName, String defaultFileName, List<E> list, SheetProperty<E, ?>... properties) throws IOException {
        File file = showSaveDialog(defaultFileName, "تصدير ...", "*.xlsx");
        if (file == null) {
            return;
        }
        Workbook workbook = new XSSFWorkbook();
        if (sheetName == null || sheetName.isEmpty()) {
            sheetName = "sheet";
        }
        Sheet sheet = workbook.createSheet(sheetName);
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
//        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        headerCellStyle.setBorderBottom((short) 2);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < properties.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(properties[i].getTitle());
            cell.setCellStyle(headerCellStyle);
        }
//        headerRow.setHeight((short) 20);


        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);


        UIController.mainView.addTask(
                new MainView.Task(list.size()) {
                    @Override
                    public void runTask() throws Throwable {

                        int rowNum = 1;
                        for (E contact : list) {
                            Row row = sheet.createRow(rowNum++);
                            for (int i = 0; i < properties.length; i++) {
                                Cell cell = row.createCell(i);
                                cell.setCellStyle(cellStyle);
                                writeToCell(contact, cell, properties[i]);
                            }
                            setProgress(rowNum - 1);
                        }

                        CellStyle headerCellStyle = workbook.createCellStyle();
                        headerCellStyle.setFont(headerFont);
                        headerCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
                        headerCellStyle.setBorderTop((short) 2);
                        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

                        ///total
                        Row row = sheet.createRow(rowNum++);
                        for (int i = 0; i < properties.length; i++) {
                            Cell cell = row.createCell(i);
                            cell.setCellValue(properties[i].total);
                            cell.setCellStyle(headerCellStyle);
                        }
                        if (list.size() < 500)
                            for (int i = 0; i < properties.length; i++) {
                                sheet.autoSizeColumn(i);
                            }

                        FileOutputStream fileOut = new FileOutputStream(file);
                        workbook.write(fileOut);
                        fileOut.close();
                        Platform.runLater(() -> new ShowFilePath(file));
                    }
                }
        );

    }

    private static <E, V> void writeToCell(E contact, Cell cell, SheetProperty<E, V> property) {
        property.writeToCell(cell, property.read(contact));
    }

    public static void writeFile(File file, String text) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(text);
        myWriter.close();
    }

    static public String ReadFileFromRecourse(String FileName, Object FileClass) {
        String Content = "";
        Content = ReadFileFromRecourse(new InputStreamReader(FileClass.getClass().getResourceAsStream(FileName), StandardCharsets.UTF_8));
        return Content;
    }

    static public String ReadFileFromRecourse(Reader reader) {
        String content = "";
        StringBuffer temp = new StringBuffer(1024);
        char[] buffer = new char[1024];
        int read;
        try {
            while ((read = reader.read(buffer, 0, buffer.length)) != -1) {
                temp.append(buffer, 0, read);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        content = temp.toString();
        return content;
    }

    public static void print(String page) throws IOException {
        File tempDir = new File("PrintingTemp/");
        tempDir.mkdir();
        File file = new File(tempDir.getAbsolutePath() + "/temp.html");
        writeFile(file, page);
        Desktop.getDesktop().print(file);
        Desktop.getDesktop().open(tempDir);
    }

    public static void zipFileWithPassword(List<String> srcFiles, String ZipFilePath, char[] pass) throws IOException {
        ZipFile zipFile = new ZipFile(ZipFilePath, pass);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        parameters.setEncryptFiles(true);

        //Set the encryption method to AES Zip Encryption
        parameters.setEncryptionMethod(EncryptionMethod.AES);

        //AES_STRENGTH_128 - For both encryption and decryption
        //AES_STRENGTH_192 - For decryption only
        //AES_STRENGTH_256 - For both encryption and decryption
        //Key strength 192 cannot be used for encryption. But if a zip file already has a
        //file encrypted with key strength of 192, then Zip4j can decrypt this file
        parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

        //Now add files to the zip file
        zipFile.addFiles(mapList(srcFiles, File::new), parameters);
    }

    public static <X, Y> List<Y> mapList(Collection<X> from, Function<? super X, ? extends Y> mapper) {
        return from.stream().map(mapper).collect(Collectors.toCollection(ArrayList::new));
    }

    public static <X, Y> List<Y> mapList(X[] from, Function<? super X, ? extends Y> mapper) {
        return Stream.of(from).map(mapper).collect(Collectors.toCollection(ArrayList::new));
    }

    static public void exportDbs(SQLiteHelper... link) throws IOException {

        File file = IO.showSaveDialog(JDateTime.addDateToFileName("HRApp", LocalDateTime.now(), ".jsef"),
                "تصدير ...", "*.jsef");
        if (file != null) {
            IO.zipFileWithPassword(IO.mapList(link, SQLiteHelper::getFilePath), file.getAbsolutePath(), pwd);
            Platform.runLater(() -> new ShowFilePath(file));
        }
    }

    static public void importDbs(SQLiteHelper... link) throws Exception {
        try {
            File file = IO.showOpenDialog("إستيراد ...", "*.jsef");
            if (file != null) {
                if (UIController.mainView.isInProgress()) {
                    throw new ZValidate(UIController.mainView.getProgressBar(),
                            "Can't do this process right now,\nplease try again after application done with all tasks.");
                }
                for (SQLiteHelper sqLiteHelper : link) {
                    File dbPath = new File(sqLiteHelper.getFilePath());
                    sqLiteHelper.getConnection().close();
                    new ZipFile(file, pwd).extractFile(dbPath.getName(), sqLiteHelper.getFilePath());
                    sqLiteHelper.openConnection();
                }
                Toast.SucssesToast("إستيراد", "تم إستيراد قواعد البيانات بنجاح");
            }
        } catch (ZipException e) {
            throw new Exception("!Bad File.");
        }
    }

    static public void extractFile(File file, char[] password, String fileName, String outputFile) throws Exception {
        ;
    }

    public static class SheetProperty<E, V> extends Property<E, V> {
        private String total = "";

        public SheetProperty(String title, Func<E, V> reader) {
            super(title, reader);
        }

        public SheetProperty(String title, Func<E, V> reader, String total) {
            super(title, reader);
            this.total = total;
        }

        public V read(E e) {
            try {
                return getReader().apply(e);
            } catch (Exception e1) {
                return null;
            }
        }

        public void writeToCell(Cell cell, V read) {
            if (read != null) {
                cell.setCellValue(read.toString());
            } else {
                cell.setCellValue((String) null);
            }
        }
    }
}
