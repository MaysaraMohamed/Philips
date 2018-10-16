package com.philips.backend.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.common.Response;
import com.philips.backend.dao.User;
import com.philips.backend.encryption.Encryption;
import com.philips.backend.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

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

	// should be updated to return error code.
	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public Response postUniqUser(@RequestBody User user) {
		Response response = new Response(); 
		List<User> usersList = userRepository.findByUserName(user.getUserName());
		if (usersList.size() == 1) {
			user.setId(usersList.get(0).getId());
			user.setPassword(Encryption.encrypt(user.getPassword()));
			userRepository.save(user);
			response.setStatus(200);
			response.setMessage(user.getUserName());
		} else {
			response.setStatus(404);
			response.setMessage("Not Found");
		}
		return response; 
	}

}
