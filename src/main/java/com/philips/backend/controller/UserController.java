package com.philips.backend.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.dao.Response;
import com.philips.backend.dao.User;
import com.philips.backend.encryption.Encryption;
import com.philips.backend.logic.MailManagement;
import com.philips.backend.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailManagement mailManagement;  

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public Response checkLogin(@RequestBody User user) {
		Response response = new Response();
		List<User> usersList = userRepository.findByUserName(user.getUserName());
		if (usersList.size() == 0) {
			response.setMessage("Unauthorized");
			response.setStatus(401);
			LOGGER.info("User is : Unauthorized");
		} else if (Encryption.encrypt(user.getPassword()).equals(usersList.get(0).getPassword())) {
			response.setMessage("SUCCESS");
			response.setStatus(200);
			LOGGER.info("User login successfully");
		} else {
			response.setMessage("Forbidden");
			response.setStatus(403);
			LOGGER.info("User is : Forbidden");
		}
		return response;
	}
	
	// put user based on sent data on user object in request. 
	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public Response updateUser(@RequestBody User user) {
		Response response = new Response();
		List<User> usersList = userRepository.findByUserName(user.getUserName());
		User DBUser;
		
		if (usersList.size() == 1) {
			DBUser = usersList.get(0);
			DBUser.setId(DBUser.getId());
			
			if(user.getPassword() != null && ! user.getPassword().equals("")) {
				DBUser.setPassword(Encryption.encrypt(user.getPassword()));
			}
			if(user.getName() != null && ! user.getName().equals("")) {
				DBUser.setName(user.getName());
			}
			if(user.getUserName() != null && ! user.getUserName().equals("")) {
				DBUser.setUserName(user.getUserName());
			}
			if(user.getBirthDate() != null && ! user.getBirthDate().equals("")) {
				DBUser.setBirthDate(user.getBirthDate());
			}
			if(user.getTitle() != null && ! user.getTitle().equals("")) {
				DBUser.setTitle(user.getTitle());
			}
			if(user.getMail() != null && ! user.getMail().equals("")) {
				DBUser.setMail(user.getMail());
			}
			if(user.getPhone() != null && ! user.getPhone().equals("")) {
				DBUser.setPhone(user.getPhone());
			}
			if(user.getAddress() != null && ! user.getAddress().equals("")) {
				DBUser.setAddress(user.getAddress());
			}
			if(user.getUserType() != null && ! user.getUserType().equals("")) {
				DBUser.setUserType(user.getUserType());
			}
			if(user.getProfileImage() != null && ! user.getProfileImage().equals("")) {
				DBUser.setProfileImage(user.getProfileImage());
			}
			
			userRepository.save(DBUser); 
			response.setStatus(200);
			response.setMessage(DBUser.getUserName());
		}else {
			response.setStatus(404);
			response.setMessage("Not Found");
		}
		return response; 
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendpassword")
	public Response sendPassword(@RequestBody User user) {
		Response response = new Response();
		// TODO get user by ID. 
		List<User> usersList = userRepository.findByUserName(user.getUserName());
		User DBUser; 
		boolean mailSent = false; 
		
		if (usersList.size() == 0) {
			response.setMessage("User not found");
			response.setStatus(404);
			LOGGER.warning("User not found");
		}else if(usersList.get(0).getPassword() == null){
			response.setMessage("User not register before");
			response.setStatus(403);
			LOGGER.warning("User not register before. please register first");
		}else {
			DBUser = usersList.get(0); 
			// use the mail for retrieved  user. 
			String userMail = user.getMail(); 
			// call generate password method. 
			String tempPassword = mailManagement.generatePassword(); 
			// send mail with generated password. 
			try {
				mailSent = mailManagement.sendmail(tempPassword, userMail);
			} catch (AddressException e) {
				LOGGER.warning("AddressException " +e.toString() );
				response.setMessage("ERROR in mail address");
				response.setStatus(400);
				return response; 
			} catch (MessagingException e) {
				LOGGER.warning("MessagingException " +e.toString() );
				response.setMessage("ERROR in message");
				response.setStatus(400);
				return response; 
			} catch (IOException e) {
				LOGGER.warning("IOException " +e.toString() );
				response.setMessage("IO ERROR");
				response.setStatus(400);
				return response; 
			}catch (Exception e) {
				LOGGER.warning("General " +e.toString() );
				response.setMessage("ERROR Check Mail address");
				response.setStatus(500);
				return response; 
			}
			// return temp password to user to user to compare with the one sent by mail and rest his password. .
			
			// update user password with generated password.
			if(mailSent) {
				DBUser.setId(DBUser.getId());
				DBUser.setPassword(Encryption.encrypt(tempPassword));
				userRepository.save(DBUser);
				response.setMessage("SUCCESS");
				response.setStatus(200);
			}
		}
		return response;
	}
	
	
}
