package com.nopCommerce.helpClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataExtractor {

	/**@author Vinod Kadam
	 * @param excelPath = Excel location in files/project
	 * @param sheetName = Sheet Name
	 * @return It return XSSFSheet(Sheet)
	 */
	public static XSSFSheet workbookInit(String excelPath, String sheetName) {
		XSSFSheet sh = null;
		try (FileInputStream fis = new FileInputStream(excelPath); XSSFWorkbook wb = new XSSFWorkbook(fis);) {
			sh = wb.getSheet(sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sh;
	}

	
	/**
	 * @param excelPath = Excel location in files/project
	 * @param sheetName = Sheet Name
	 * @param testCase = Test Case Number
	 * @return This method return all rowID's which is match with provided testcase number
	 * 
	 */
	public static LinkedList<Integer> rowID_Return(String excelPath, String sheetName, String testCase) {
		LinkedList<Integer> rowID = new LinkedList<>();

		XSSFSheet sh = workbookInit(excelPath, sheetName);

		int totalRows = sh.getPhysicalNumberOfRows(); // Store total numbers of rows count
		for (int i = 0; i < totalRows; i++) {
			XSSFRow row = sh.getRow(i); // Pointing to row i
			if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCase)) { // It compare 1st value of cell with
																					// testCase1
				rowID.add(i); // Add row id in "rowID"
			}
		}
		return rowID;
	}

	/**
	 * @param rowID = Row id
	 * @param excelPath = Excel location in files/project
	 * @param sheetName = Sheet Name
	 * @return It return data from provided row ID
	 */
	public static LinkedList<String> rowDataReturn_UsingRowID(int rowID, String excelPath, String sheetName) {
		LinkedList<String> storeValue = new LinkedList<>();
		XSSFSheet sh = workbookInit(excelPath, sheetName);
		XSSFRow row1 = sh.getRow(rowID); // Pointing to one row using rowID
		for (int j = 0; j < row1.getLastCellNum(); j++) {
			XSSFCell cell = row1.getCell(j); // Get perticular cell data and rtore it into cell
			String value = null;
			switch (cell.getCellType()) {
			case STRING:
				value = cell.getStringCellValue(); // Get cell data as per cell type but we only use string
				break;
			default:
				value = cell.getStringCellValue();
			}
			storeValue.add(value); // Store cell value in Linkedlist(Becoz it maintain insertion order)
		}
		return storeValue;
	}

	/**
	 * @param rowID = Row id
	 * @param excelPath = Excel location in files/project
	 * @param sheetName = Sheet Name
	 * @return It return data in form of key value
	 */
	public static LinkedHashMap<String, String> KeyValueDataReturnForMultipleRowMatch(int rowID, String excelPath, String sheetName) {
		LinkedHashMap<String, String> storeKeyValue = new LinkedHashMap<>();
		LinkedList<String> tempKeyStore = new LinkedList<>();

		XSSFSheet sh = workbookInit(excelPath, sheetName);

		/*
		 * Get top line data and store it into key
		 */
		XSSFRow row = sh.getRow(0);
		DataFormatter formatterForKey = new DataFormatter();  // DataFormatter check all data formate
		String key = null;
		for (int i = 0; i < row.getLastCellNum(); i++) {
			XSSFCell cell = row.getCell(i);
			key = formatterForKey.formatCellValue(cell);
			tempKeyStore.add(key); // Store key in linkedList also to use while storing value in LinkedHashMap
			storeKeyValue.put(key, null); // Store Key in LinkedHashMap and store value as null
		}
		
		/*
		 * Get data as per testcase and store it in value		
		 */		
		String value = null;
		DataFormatter formatterForValue = new DataFormatter();
		XSSFRow row1 = sh.getRow(rowID); // rowID.get(1) Use rowId to get perticular rowID data
		for (int j = 0; j < row1.getLastCellNum(); j++) {
			XSSFCell cell = row1.getCell(j);
			value = formatterForValue.formatCellValue(cell);
			storeKeyValue.put(tempKeyStore.get(j), value); // put value as per key position
		}

		return storeKeyValue;
	}

}
