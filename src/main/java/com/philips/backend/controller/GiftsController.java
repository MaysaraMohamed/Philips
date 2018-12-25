package com.philips.backend.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.common.GiftsResponse;
import com.philips.backend.dao.Gifts;
import com.philips.backend.dao.PointsHistory;
import com.philips.backend.dao.Response;
import com.philips.backend.dao.Users;
import com.philips.backend.repository.GiftsRepository;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.UserRepository;

@RestController
public class GiftsController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PointsHistoryRepository pointsHistoryRepository;
	@Autowired
	private GiftsRepository giftsRepository;

	private static final Logger LOGGER = Logger.getLogger(GiftsController.class.getName());

	@RequestMapping("/gifts/{userName}")
	public GiftsResponse getGifts(@PathVariable String userName) {
		double totalPoints;
		GiftsResponse giftsResponse;
		try {
			Users user = userRepository.findByUserNameIgnoreCase(userName).get(0);
			giftsResponse = new GiftsResponse();
			totalPoints = pointsHistoryRepository.getTotalUserNetPoints(user);
			giftsResponse.setTotalPoints(totalPoints);
			giftsResponse
					.setGifts(giftsRepository.findByUserTypeAndPointsLessThanEqual(user.getUserType(), totalPoints));
		} catch (Exception e) {
			return null;
		}
		return giftsResponse;
	}

	@RequestMapping("/allGifts/{userType}")
	public GiftsResponse getGiftsByUserType(@PathVariable String userType) {
		GiftsResponse giftsResponse = new GiftsResponse();
		try {
			List<Gifts> gifts = giftsRepository.findByUserTypeOrderByPointsAsc(userType);
			giftsResponse.setGifts(gifts);

		} catch (Exception e) {
			return null;
		}
		return giftsResponse;
	}

	// I used user her to update points instead of creating new object to just send
	// data in request.
	@RequestMapping(method = RequestMethod.POST, value = "/updatePoints")
	public Response updatePoints(@RequestBody Users userRequest) {
		LOGGER.info("Request is : " + userRequest.toString());
		Response response = new Response();
		try {
			double usedPoints = userRequest.getTotalPoints();
			Users user = userRepository.findByUserNameIgnoreCase(userRequest.getUserName()).get(0);
			user.setTotalPoints(user.getTotalPoints() - usedPoints);
			PointsHistory pointsRecord = pointsHistoryRepository.getLastPointsRecord(user).get(0);
			pointsRecord.setUsedPoints(pointsRecord.getUsedPoints() + usedPoints);
			pointsHistoryRepository.save(pointsRecord);
			userRepository.save(user);
			response.setStatus(200);
			response.setMessage("SUCCESS");
		} catch (Exception e) {
			response.setStatus(400);
			response.setMessage("FAILED");
			e.printStackTrace(System.out);
		}
		return response;

	}

}
