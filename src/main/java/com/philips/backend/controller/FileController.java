package com.philips.backend.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.logic.FileManagement;

@RestController
public class FileController {
	
	@Autowired
	FileManagement fileManagement; 
	private static final Logger LOGGER = Logger.getLogger(FileController.class.getName());
	
	@Value("${com.philips.backend.excel.users.path}")
	private String excelPath;
	
	// should be updated to return error code.
	@RequestMapping(method = RequestMethod.GET, value = "/loadUsers")
	public String loadDataToDB() {

		try {
		String filePath = fileManagement.readFilesFromDirectory("xlsx", excelPath);
		fileManagement.readUsersFromExcel(filePath);
		}catch (Exception e) {
			LOGGER.warning(e.toString());
			return "Something wong please try again later"; 
		}
		return "Data Loaded";
	}

}
