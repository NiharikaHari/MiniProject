package com.amazon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelFile {

	public static void writeToExcel(String excelFileName, Object[] data) {
		
		String excelFilePath="output/"+excelFileName;
		
		try {

			File excelFile = new File(excelFilePath);
			XSSFWorkbook workbook;
			XSSFSheet sheet;
			int rowNum;
			FileInputStream inputStream=null;
			boolean fileExists = false;
			
			if (excelFile.isFile()) {
				inputStream = new FileInputStream(excelFile);
				workbook = new XSSFWorkbook(inputStream);
				sheet = workbook.getSheetAt(0);
				rowNum = sheet.getLastRowNum()+1;
				fileExists = true;
			} else{
				workbook = new XSSFWorkbook();
				sheet = workbook.createSheet("CartAmountSheet");
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("Product Name");
				row.createCell(1).setCellValue("Cart Amount");
				rowNum = 1;
			}

			Row row = sheet.createRow(rowNum);

			int columnCount = 0;

			for (Object value : data) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue((String) value);
			}
			
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			
			if(fileExists)
				inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(excelFilePath);
			workbook.write(outputStream);
			workbook.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}