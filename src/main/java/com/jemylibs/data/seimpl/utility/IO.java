package com.jemylibs.data.seimpl.utility;

import com.jemylibs.gdb.properties.Func;
import com.jemylibs.gdb.properties.Property;
import com.jemylibs.uilib.UIController;
import com.jemylibs.uilib.windows.ShowFilePath;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IO {

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

    public static <E> void ExportXlxs(String sheetName, String defaultFileName, List<E> list,
                                      SheetProperty<E, ?>... properties) throws IOException {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("تصدير ...");
        chooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx")
        );
        chooser.setInitialFileName(defaultFileName);
        File file = chooser.showSaveDialog(UIController.mainStage);

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

        int rowNum = 1;

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        for (E contact : list) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < properties.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                writeToCell(contact, cell, properties[i]);
            }
        }


        headerCellStyle = workbook.createCellStyle();
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
//        row.setHeight((short) 20);

        for (int i = 0; i < properties.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();

        new ShowFilePath(chooser.getTitle(), file);
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
