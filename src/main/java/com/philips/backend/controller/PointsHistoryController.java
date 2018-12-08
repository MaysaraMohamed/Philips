package com.philips.backend.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.dao.Users;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.UserRepository;

@RestController
public class PointsHistoryController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PointsHistoryRepository pointsHistoryRepository;
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@RequestMapping("/userPointsHistory/{userName}")
	public Object getPointsHistoryRequest(@PathVariable String userName) {
		try {
			Users user = userRepository.findByUserName(userName).get(0);
			return pointsHistoryRepository.findTop15ByUserOrderByPointsDateDesc(user);
		} catch (Exception e) {
			LOGGER.warning(e.toString());
			return null;
		}
	}

}