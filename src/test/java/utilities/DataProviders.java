package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "LoginData")
	public String[][] getData() throws IOException {

		// Taking Excel file from testData folder
		String path = ".\\testData\\OpenCartLoginData.xlsx";

		// Creating an object of ExcelUtility
		ExcelUtility xUtil = new ExcelUtility(path);

		// Get total used rows and columns from Sheet1
		int totalRows = xUtil.getRowCount("Sheet1");
		int totalCols = xUtil.getCellCount("Sheet1", 1);

		// Creating a 2D array to store Excel data
		// totalRows x totalCols
		String loginData[][] = new String[totalRows][totalCols];

		// Loop through rows & columns of Excel sheet
		for (int i = 1; i <= totalRows; i++) { // i starts from 1 (skipping header)
			for (int j = 0; j < totalCols; j++) {
				// Fetch cell data and store into 2D array
				loginData[i - 1][j] = xUtil.getCellData("Sheet1", i, j);
			}
		}

		// Returning a two-dimensional array for DataProvider consumption
		return loginData;
	}

}
