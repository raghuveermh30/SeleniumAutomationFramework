package com.qa.opencart.utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtil {

    private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/openCartTestdata.xlsx";
    private static Workbook book;
    private static Sheet sheet;
    private static Object data[][];


    public static Object[][] getTestData(String sheetName) {

        try {
            FileInputStream fileInputStream = new FileInputStream(TEST_DATA_SHEET_PATH);
            book = WorkbookFactory.create(fileInputStream);
            sheet = book.getSheet(sheetName);

            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    data[i][j] = sheet.getRow(i+1).getCell(j).toString();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return data;
    }
}
