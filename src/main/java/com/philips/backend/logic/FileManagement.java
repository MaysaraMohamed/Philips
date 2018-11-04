package com.philips.backend.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.philips.backend.common.Utilities;
import com.philips.backend.controller.FileController;
import com.philips.backend.dao.Category;
import com.philips.backend.dao.PhilipsInvoice;
import com.philips.backend.dao.PhilipsInvoiceCategories;
import com.philips.backend.dao.Users;
import com.philips.backend.repository.CategoryRepository;
import com.philips.backend.repository.PhilipsInvoiceCategoriesRepository;
import com.philips.backend.repository.PhilipsInvoiceRepository;
import com.philips.backend.repository.UserRepository;

//import javafx.scene.control.Cell;

@Controller
public class FileManagement {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PhilipsInvoiceCategoriesRepository philipsInvoiceCategoriesRepository;

	@Autowired
	private PhilipsInvoiceRepository philipsInvoiceRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());

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
	public void loadUsersFromExcel(String filePath) {
		FileInputStream fis = null;
		String userType = "";
		// set customer type based on Input file.
		// EX if file contains
		if (Utilities.stringContainsNumber(filePath)) {
			userType = Utilities.getNumberOnly(filePath);
		}
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
			Users user = new Users();
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
					user.setUserType(userType);
					break;
				default:
				}
			}
			System.out.print(user.toString() + "\n");
			System.out.println("");
			if (user.getUserName() != null) {
				userRepository.save(user);
			}
		}
	}

	public void loadInvoicesFromExcel(String filePath) {
		FileInputStream fis = null;
		List<String> invoiceRecored;
		PhilipsInvoice philipsInvoice = null;
		PhilipsInvoiceCategories philipsInvoiceCategories;
		boolean cleanOnce = true; 

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
		while (rowIterator.hasNext()) {
			philipsInvoiceCategories = new PhilipsInvoiceCategories();
			invoiceRecored = new ArrayList<String>();
			org.apache.poi.ss.usermodel.Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					invoiceRecored.add(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					invoiceRecored.add(cell.getNumericCellValue() + "");
				default:
				}
			}
			// To skip all unused records and skip the header
			// Example to understand order [Category, Sales ID, Invoice Date, Customer Code,
			// Customer Name, NET SALE]
			if (invoiceRecored.size() >= 6 && !invoiceRecored.get(0).equals("Category")) {
				// Insert invoice only if not exist.
				philipsInvoice = new PhilipsInvoice();
				String salesId = invoiceRecored.get(1); 
				philipsInvoice.setSalesId(salesId);
				// to delete old invoice if exist and insert again. 
//				if (philipsInvoiceRepository.findById(salesId).isPresent() && cleanOnce) {
//					philipsInvoiceCategoriesRepository.deleteByPhilipsInvoice(philipsInvoice);
//					cleanOnce = false; 
//				}
					System.out.print(invoiceRecored.toString() + "\n");
					philipsInvoiceCategories.setNetSale(Double.parseDouble(invoiceRecored.get(5)));
					// prepare Philips invoice relation
					philipsInvoice.setInvoiceDate(Utilities.stringToDate(invoiceRecored.get(2)));
					Users user = new Users();
					user.setUserName(invoiceRecored.get(3).replace(" ", ""));
					philipsInvoice.setUser(user);
					// here invoice will be saved more than once but because it always has the same
					// same ID it is not an issue right now.
					philipsInvoiceRepository.save(philipsInvoice);
					philipsInvoiceCategories.setPhilipsInvoice(philipsInvoice);
					String categoryName = invoiceRecored.get(0);
					Category category = categoryRepository.findByCategoryName(categoryName);
					philipsInvoiceCategories.setCategory(category);
					philipsInvoiceCategoriesRepository.save(philipsInvoiceCategories);
			}
		}
	}
}
