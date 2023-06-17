package com.justdial.qa.util;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.justdial.qa.base.TestBase;

public class TestUtil extends TestBase {
	public static int pageLoadTimout = 60;
	public static int implicitWait = 20;

	// delete cookies and hit refresh button
	public void cookieHandler() {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
	}

	// method to send TestData
	public static Object[][] getTestData() throws Exception {
		// defining the file path
		String filePath = System.getProperty("user.dir")
				+ "//src//main//java//com//justdial//qa//testdata//JustDialTestData.xlsx";
		// reading file
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook w = new XSSFWorkbook(fis);
		XSSFSheet s = w.getSheetAt(0);
		int rows = s.getPhysicalNumberOfRows(); // number of rows= s.getPhysicalNumberOfRows
		int columns = s.getRow(0).getPhysicalNumberOfCells();// number of columns= get one row -> calculate number of
																// cells used
		// 2-D array of Object type
		Object data[][] = new Object[rows - 1][columns];
		for (int i = 1; i < rows; i++) {
			XSSFRow r = s.getRow(i); // row of the sheet
			for (int j = 0; j < columns; j++) {
				Cell cell = r.getCell(j);
				switch (cell.getCellType()) {
				case STRING:
					data[i - 1][j] = cell.getStringCellValue();
					break;
				case NUMERIC:
					data[i - 1][j] = (long) cell.getNumericCellValue();
					break;
				default:
					System.out.println("Null data");
				}
			}
		}
		fis.close();
		w.close();
		return data;
	}
}
