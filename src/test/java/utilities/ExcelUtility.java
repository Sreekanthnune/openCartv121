package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;

public class ExcelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	/**
	 * Constructor to store the Excel file path
	 */
	public ExcelUtility(String path) {
		this.path = path;
	}

	/**
	 * Returns total number of used rows in a sheet
	 */
	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}

	/**
	 * Returns total number of cells in a given row
	 */
	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellcount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
	}

	/**
	 * Reads cell value as formatted text (works for numeric/boolean/string/date)
	 */
	public String getCellData(String sheetName, int rownum, int column) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(column);

		DataFormatter formatter = new DataFormatter();
		String data;

		try {
			data = formatter.formatCellValue(cell); // Formats based on cell type
		} catch (Exception e) {
			data = ""; // Return blank if exception
		}

		workbook.close();
		fi.close();
		return data;
	}

	/**
	 * Writes a string into a cell, creates missing sheet/row if required
	 */
	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {

		File xlfile = new File(path);

		// If file not exists then create a new file
		if (!xlfile.exists()) {
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
			workbook.close();
			fo.close();
		}

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);

		// If sheet not exists, then create new sheet
		if (workbook.getSheetIndex(sheetName) == -1) {
			workbook.createSheet(sheetName);
		}

		sheet = workbook.getSheet(sheetName);

		// If row not exists then create new row
		if (sheet.getRow(rownum) == null) {
			sheet.createRow(rownum);
		}

		row = sheet.getRow(rownum);

		// Create cell and write data
		cell = row.createCell(column);
		cell.setCellValue(data);

		fi.close();

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}

	/**
	 * Fills cell with GREEN background color
	 */
	public void fillGreenColor(String sheetName, int rownum, int column) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(column);

		style = workbook.createCellStyle();
		style.setFillForegroundColor(new XSSFColor(new Color(0, 255, 0), null));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);

		fi.close();

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}

	/**
	 * Fills cell with RED background color
	 */
	public void fillRedColor(String sheetName, int rownum, int column) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(column);

		style = workbook.createCellStyle();
		style.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0), null));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);

		fi.close();

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}
}
