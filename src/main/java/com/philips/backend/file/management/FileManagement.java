package com.philips.backend.file.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.philips.backend.dao.User;
import com.philips.backend.repository.UserRepository;

//import javafx.scene.control.Cell;

@Controller
public class FileManagement {

	@Autowired
	private UserRepository userRepository;

	/*
	 * Function to read files from directory by extension.
	 */
	public String readFilesFromDirectory(String fileExtension, String path) {
		String output = "";
		File f = null;
		String[] files;
		try {
			// create new file
			f = new File(path);
			// array of files and directory
			files = f.list();
			// for each name in the path array
			for (String file : files) {
				// print only Excel files
				if (file.contains(fileExtension)) {
					output = path + "/" + file;
				}
			}
		} catch (Exception e) {
			// if any error occurs
			e.printStackTrace();
		}
		return output;
	}

	/**
	 * Function to to read content from Excel file to be used to in DB insertion
	 * later.
	 * 
	 * @param filePath
	 */
	public void readContentFromExcel(String filePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = null;
		try {
			myWorkBook = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		// Get iterator to all the rows in current sheet
		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();
		// Traversing over each row of XLSX file
		boolean flag = false; 
		while (rowIterator.hasNext()) {
			org.apache.poi.ss.usermodel.Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			User user = new User();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if (!cell.getStringCellValue().equals("Name")
							&& !cell.getStringCellValue().equals("Customer account")) {
						if (flag) {
							user.setUserName(cell.getStringCellValue());
							flag = false; 
						} else {
							user.setName(cell.getStringCellValue());
							flag = true; 
						}
					}
					break;
				// Not needed for now.
				// case Cell.CELL_TYPE_NUMERIC:
				// System.out.print(cell.getNumericCellValue() + "\t");
				// break;
				// case Cell.CELL_TYPE_BOOLEAN:
				// System.out.print(cell.getBooleanCellValue() + "\t");
				// break;
				default:
				}
			}
//			System.out.print(user.toString() + "\n");
//			System.out.println("");
			userRepository.save(user); 
		}
	}
}
