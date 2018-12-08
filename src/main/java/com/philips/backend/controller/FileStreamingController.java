package com.philips.backend.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.philips.backend.common.FileStorageProperties;
import com.philips.backend.common.UploadFileResponse;
import com.philips.backend.dao.Users;
import com.philips.backend.service.FileStorageService;

@RestController
public class FileStreamingController {

	private static final Logger logger = LoggerFactory.getLogger(FileStreamingController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private UserController userController;

	@PostMapping("/uploadProfileImage")
	public UploadFileResponse uploadProfileImage(@RequestParam("file") MultipartFile file) {

		String fileName = fileStorageService.storeFile(file);

		long millis = System.currentTimeMillis(); 
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString()+"/"+millis;

		// Update profile image to subscriber here.
		Users user = new Users();
		// Get userName from profile image. Coz image name should be sent by userName.
		// split function not work with dot directly use \\ first
		user.setUserName(fileName.split("\\.")[0]);
		user.setProfileImage(fileDownloadUri);
		userController.updateUser(user);

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadUsersSheet")
	public UploadFileResponse uploadUsersSheet(@RequestParam("file") MultipartFile file) {

		FileStorageProperties fileStorageProperties = new FileStorageProperties();
		fileStorageProperties.setUploadDir("./users");
		FileStorageService fileStorageService = new FileStorageService(fileStorageProperties);
		
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		// Update profile image to subscriber here.
		Users user = new Users();
		// Get userName from profile image. Coz image name should be sent by userName.
		// split function not work with dot directly use \\ first
		user.setUserName(fileName.split("\\.")[0]);
		user.setProfileImage(fileDownloadUri);
		userController.updateUser(user);

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	
	@PostMapping("/uploadInvoicesSheet")
	public UploadFileResponse uploadInvoicesSheet(@RequestParam("file") MultipartFile file) {

		FileStorageProperties fileStorageProperties = new FileStorageProperties();
		fileStorageProperties.setUploadDir("./invoices");
		FileStorageService fileStorageService = new FileStorageService(fileStorageProperties);
		
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		// Update profile image to subscriber here.
		Users user = new Users();
		// Get userName from profile image. Coz image name should be sent by userName.
		// split function not work with dot directly use \\ first
		user.setUserName(fileName.split("\\.")[0]);
		user.setProfileImage(fileDownloadUri);
		userController.updateUser(user);

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}


	
	
	
	
	
//	@PostMapping("/uploadMultipleFiles")
//	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
//	}

	@GetMapping("/downloadFile/{fileName:.+}/*")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = fileStorageService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
