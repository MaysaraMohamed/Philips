package com.philips.backend.scheduler;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.UserRepository;

@Component
public class Daemon {

	@Autowired
	DaemonController daemonController;

	@Autowired
	PointsHistoryRepository pointsHistoryRepository;

	@Autowired
	UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(Daemon.class);

	@Transactional
	@Scheduled(fixedRateString = "${daemon.tick}")
	public void daemonExecute() {

		// get all scheduled invoices where submissionDate < sysdate - configured
		// number.
		List<SubmitedInvoice> submitedInvoices = daemonController.getScheduledInvoices();
		// match selected invoices with philips invoices.
		// update invoice status
		// once invoice matched calculate its points and update points history.
		daemonController.matchScheduledInvoices(submitedInvoices);
//		LOGGER.info(pointsHistoryRepository.getPointsRecordToday(userRepository.findByUserName("LI003092").get(0)).toString());
		// TODO send mail to user with match or not match state.
	}
}