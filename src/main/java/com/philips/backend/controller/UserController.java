package com.philips.backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.common.PointsRecord;
import com.philips.backend.dao.PointsHistory;
import com.philips.backend.dao.Response;
import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.dao.SubmitedInvoiceCategories;
import com.philips.backend.dao.Users;
import com.philips.backend.encryption.Encryption;
import com.philips.backend.logic.MailManagement;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.SubmitedInvoiceCategoriesRepository;
import com.philips.backend.repository.SubmitedInvoiceRepository;
import com.philips.backend.repository.UserRepository;
import com.philips.backend.scheduler.DaemonController;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailManagement mailManagement;

	@Autowired
	private PointsHistoryRepository pointsHistoryRepository;

	@Autowired
	private SubmitedInvoiceCategoriesRepository submitedInvoiceCategoriesRepository;

	@Autowired
	private SubmitedInvoiceRepository submitedInvoiceRepository;

	@Autowired
	private DaemonController daemonController;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public Users checkLogin(@RequestBody Users user) {
		// Response response = new Response();
		List<Users> usersList = userRepository.findByUserName(user.getUserName());
		if (usersList.size() == 0) {
			LOGGER.info("User is : Unauthorized");
			return null;
		} else if (Encryption.encrypt(user.getPassword()).equals(usersList.get(0).getPassword())) {
			LOGGER.info("User login successfully");
			return usersList.get(0);
		} else {
			LOGGER.info("User is : Forbidden");
			return null;
		}
	}

	// put user based on sent data on user object in request.
	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public Users updateUser(@RequestBody Users user) {
		List<Users> usersList = userRepository.findByUserName(user.getUserName());
		Users DBUser;

		if (usersList.size() == 1) {
			DBUser = usersList.get(0);
			if (user.getPassword() != null && !user.getPassword().equals("")) {
				DBUser.setPassword(Encryption.encrypt(user.getPassword()));
			}
			if (user.getName() != null && !user.getName().equals("")) {
				DBUser.setName(user.getName());
			}
			if (user.getUserName() != null && !user.getUserName().equals("")) {
				DBUser.setUserName(user.getUserName());
			}
			if (user.getBirthDate() != null) {
				DBUser.setBirthDate(user.getBirthDate());
			}
			if (user.getTitle() != null && !user.getTitle().equals("")) {
				DBUser.setTitle(user.getTitle());
			}
			if (user.getMail() != null && !user.getMail().equals("")) {
				DBUser.setMail(user.getMail());
			}
			if (user.getPhone() != null && !user.getPhone().equals("")) {
				DBUser.setPhone(user.getPhone());
			}
			if (user.getAddress() != null && !user.getAddress().equals("")) {
				DBUser.setAddress(user.getAddress());
			}
			if (user.getUserType() != null && !user.getUserType().equals("")) {
				DBUser.setUserType(user.getUserType());
			}
			if (user.getProfileImage() != null && !user.getProfileImage().equals("")) {
				DBUser.setProfileImage(user.getProfileImage());
			}
			return userRepository.save(DBUser);
		} else {
			return null;
		}
	}

	// put user based on sent data on user object in request.
	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword")
	public Response changePassword(@RequestBody Users user) {
		Response response = new Response();
		if (checkLogin(user) != null) {
			Users newUser = new Users();
			newUser.setPassword(user.getNewPassword());
			newUser.setUserName(user.getUserName());
			if (updateUser(newUser) != null) {
				response.setStatus(200);
				response.setMessage("Password updated successfully");
				return response;
			} else {
				response.setMessage("ERROR in updating password");
				response.setStatus(401);
				return response;
			}
		} else {
			response.setMessage("Old password not correct");
			response.setStatus(400);
			return response;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/sendpassword")
	public Response sendPassword(@RequestBody Users user) {
		Response response = new Response();
		// TODO get user by ID.
		List<Users> usersList = userRepository.findByUserName(user.getUserName());
		Users DBUser;
		boolean mailSent = false;

		if (usersList.size() == 0) {
			response.setMessage("User not found");
			response.setStatus(404);
			LOGGER.warning("User not found");
		} else if (usersList.get(0).getPassword() == null) {
			response.setMessage("User not register before");
			response.setStatus(403);
			LOGGER.warning("User not register before. please register first");
		} else {
			DBUser = usersList.get(0);
			// use the mail for retrieved user.
			String userMail = user.getMail();
			// call generate password method.
			String tempPassword = mailManagement.generatePassword();
			// send mail with generated password.
			try {
				mailSent = mailManagement.sendmail("", tempPassword, userMail);
			} catch (AddressException e) {
				LOGGER.warning("AddressException " + e.toString());
				response.setMessage("ERROR in mail address");
				response.setStatus(400);
				return response;
			} catch (MessagingException e) {
				LOGGER.warning("MessagingException " + e.toString());
				response.setMessage("ERROR in message");
				response.setStatus(400);
				return response;
			} catch (IOException e) {
				LOGGER.warning("IOException " + e.toString());
				response.setMessage("IO ERROR");
				response.setStatus(400);
				return response;
			} catch (Exception e) {
				LOGGER.warning("General " + e.toString());
				response.setMessage("ERROR Check Mail address");
				response.setStatus(500);
				return response;
			}
			// return temp password to user to user to compare with the one sent by mail and
			// rest his password. .

			// update user password with generated password.
			if (mailSent) {
				// DBUser.setId(DBUser.getId());
				DBUser.setPassword(Encryption.encrypt(tempPassword));
				userRepository.save(DBUser);
				response.setMessage("SUCCESS");
				response.setStatus(200);
			}
		}
		return response;
	}

	@RequestMapping("/userProfile/{userId}")
	public Object getUserProfileData(@PathVariable String userId) {
		try {
			Users user = userRepository.findByUserName(userId).get(0);
			double totalNetPoints = pointsHistoryRepository.getTotalUserNetPoints(user);
			double totalRedeemedPoints = pointsHistoryRepository.getTotalRedeemedPoints(user);
			double totalPointsTileDate = pointsHistoryRepository.getTotalUserPoints(user);
			user.setTotalPoints(totalNetPoints);
			user.setTotalRedeemedPoints(totalRedeemedPoints);
			user.setTotalPointsTileDate(totalPointsTileDate);
			user.setTotalPendingPoints(calculatePendingPoints());
			return user;
		} catch (Exception e) {
			LOGGER.warning(e.toString());
		}
		return null;
	}

	@RequestMapping("/pointsHistory/{userId}")
	public Object getUserPointsHistory(@PathVariable String userId) {
		try {
			Users user = userRepository.findByUserName(userId).get(0);
			List<PointsRecord> points = new ArrayList<>();
			for (PointsHistory point : pointsHistoryRepository.findByUser(user)) {
				PointsRecord record = new PointsRecord();
				record.setPoints(point.getPoints());
				record.setPointsDate(point.getPointsDate());
				record.setUsedPoints(point.getUsedPoints());
				record.setUserName(point.getUser().getUserName());
				points.add(record);
			}
			return points;
		} catch (Exception e) {
			LOGGER.warning(e.toString());
		}
		return null;
	}

	public double calculatePendingPoints() {
		double totalNetSale = 0;
		// Get scheduled sales ID,
		// Get totalNet sale for each sales ID
		// get Total net sale for all sales ID.
		for (SubmitedInvoice submitedInvoice : submitedInvoiceRepository.findByStatus("SCHEDULED")) {
			for (SubmitedInvoiceCategories submitedInvoiceCategories : submitedInvoiceCategoriesRepository
					.findBySubmitedInvoice(submitedInvoice)) {
				totalNetSale += submitedInvoiceCategories.getNetSale();
			}
		}
		// convert total sales ID to points.
		return daemonController.getPointsForNetSale(totalNetSale);
	}

}
