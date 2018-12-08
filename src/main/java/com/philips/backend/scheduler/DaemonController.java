package com.philips.backend.scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.philips.backend.controller.InvoiceController;
import com.philips.backend.dao.PointsHistory;
import com.philips.backend.dao.PointsMapping;
import com.philips.backend.dao.Response;
import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.dao.SubmitedInvoiceCategories;
import com.philips.backend.dao.Users;
import com.philips.backend.logic.MailManagement;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.PointsMappingRepository;
import com.philips.backend.repository.SubmitedInvoiceCategoriesRepository;
import com.philips.backend.repository.SubmitedInvoiceRepository;
import com.philips.backend.repository.UserRepository;

@Component
public class DaemonController {

	@Autowired
	private SubmitedInvoiceRepository submitedInvoiceRepository;

	@Autowired
	private SubmitedInvoiceCategoriesRepository submitedInvoiceCategoriesRepository;

	@Autowired
	private DaemonConfigurations daemonConfigurations;

	@Autowired
	private InvoiceController invoiceController;

	@Autowired
	private PointsHistoryRepository pointsHistoryRepository;

	@Autowired
	private MailManagement mailManagement;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PointsMappingRepository pointsMappingRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(Daemon.class);

	public List<SubmitedInvoice> getScheduledInvoices() {
		List<SubmitedInvoice> submitedInvoices = new ArrayList<>();
		List<SubmitedInvoice> selectednvoices;
		selectednvoices = submitedInvoiceRepository.findByStatus("SCHEDULED");
		for (SubmitedInvoice submitedInvoice : selectednvoices) {
			if (submitedInvoice.getSubmissionDate().getTime() < new Date().getTime()
					- daemonConfigurations.getMatchAfter()) {
				submitedInvoices.add(submitedInvoice);
			}
		}
		return submitedInvoices;
	}

	/**
	 * matchScheduledInvoices and send Notification with matching result.
	 * 
	 * @param list
	 *            of scheduledInvoices to be matched.
	 */
	public void matchScheduledInvoices(List<SubmitedInvoice> scheduledInvoices) {
		Response response;
		for (SubmitedInvoice submitedInvoice : scheduledInvoices) {
			response = new Response();
			response = invoiceController.matchInvoice(submitedInvoice.getSalesId());
			submitedInvoice.setStatus(response.getStatus() + "");
			submitedInvoice = calculatePoints(submitedInvoice);
			submitedInvoiceRepository.save(submitedInvoice);
			sendMatchingResultNotification(response, submitedInvoice.getUser().getMail());
		}
	}

	/**
	 * Calculate points for Submitted invoices and submit result to points history
	 * for each user.
	 * 
	 * @param submitedInvoice
	 * @return
	 */
	public SubmitedInvoice calculatePoints(SubmitedInvoice submitedInvoice) {
		double invoicePoints = 0;
		double totalNetsale = 0;
		Users user = submitedInvoice.getUser();
		PointsHistory pointsHistory = new PointsHistory();
		if (submitedInvoice.getStatus().replaceAll(" ", "").equals("200")) {
			List<SubmitedInvoiceCategories> submitedInvoiceCategories = submitedInvoiceCategoriesRepository
					.findBySubmitedInvoice(submitedInvoice);
			for (SubmitedInvoiceCategories submitedInvoiceCategory : submitedInvoiceCategories) {
				totalNetsale += submitedInvoiceCategory.getNetSale();
			}
			// calculate total invoice points for user.
			invoicePoints = getPointsForNetSale(totalNetsale);
			// get points history for first time if not exist insert new one
			// if exist get old one and compare date to current date if match accumulate to
			// same record.

			if (pointsHistoryRepository.getPointsRecordToday(user) != null)
				pointsHistory = pointsHistoryRepository.getPointsRecordToday(user);
			submitedInvoice.setInvoicePoints(invoicePoints);
			// set points date to be sysdate and prepare points history record.
			pointsHistory.setPointsDate(new Date());
			pointsHistory.setPoints(pointsHistory.getPoints() + invoicePoints);
			pointsHistory.setUser(user);
			pointsHistoryRepository.save(pointsHistory);
			user.setTotalPoints(pointsHistoryRepository.getTotalUserPoints(user));
			userRepository.save(user);

		}
		return submitedInvoice;
	}

	private void sendMatchingResultNotification(Response matchingInvoiceResponse, String mailTo) {
		try {
			if (matchingInvoiceResponse.getStatus() == 200) {
				LOGGER.info("Sending mail with success submition to :" + mailTo + ":");
				mailManagement.sendmail("Your Invoice is submitted Successfully", "Submition Invoice result", mailTo);
			} else {
				LOGGER.info("Sending mail with failed submition to :" + mailTo + ":");
				mailManagement.sendmail("Your Invoice is not matched please try to submit again",
						"Submition Invoice result", mailTo);
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getPointsForNetSale(double totalNetSale) {
		double total = 0;
		PointsMapping lastPointMap = null;
		for (PointsMapping pointMap : pointsMappingRepository.findAllByOrderByNetSaleDesc()) {
			if (totalNetSale >= pointMap.getNetSale()) {
				total += pointMap.getPoints();
				totalNetSale = totalNetSale - pointMap.getNetSale();
			}
			lastPointMap = pointMap;
		}
		if (totalNetSale >= lastPointMap.getNetSale()) {
			total += lastPointMap.getPoints();
			totalNetSale = totalNetSale - lastPointMap.getNetSale();
		}
		return total;
	}
}
