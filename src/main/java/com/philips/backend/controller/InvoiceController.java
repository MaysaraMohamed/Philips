package com.philips.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philips.backend.common.Invoice;
import com.philips.backend.common.Utilities;
import com.philips.backend.dao.PhilipsInvoice;
import com.philips.backend.dao.PhilipsInvoiceCategories;
import com.philips.backend.dao.Response;
import com.philips.backend.dao.Users;
import com.philips.backend.repository.PhilipsInvoiceCategoriesRepository;
import com.philips.backend.repository.PhilipsInvoiceRepository;
import com.philips.backend.repository.UserRepository;

@RestController
public class InvoiceController {

	@Autowired
	private PhilipsInvoiceCategoriesRepository philipsInvoiceCategoriesRepository;

	@Autowired
	private PhilipsInvoiceRepository philipsInvoiceRepository;

	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@RequestMapping("/philipsInvoice/{invoiceId}")
	public Object getPhilipsInvoiceRequest(@PathVariable String invoiceId) {
		Response response = new Response();
		Invoice invoice = getPhilipsInvoice(invoiceId);
		if (invoice.getSalesId() != null) {
			return invoice;
		} else {
			// return not found.
			response.setStatus(404);
			response.setMessage("NOT FOUND");
			return response;
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/matchInvoices")
	public Response checkLogin(@RequestBody Invoice submitedInvoice) {
		Response response = new Response();
		Invoice invoice = getPhilipsInvoice(submitedInvoice.getSalesId());
		if (invoice.getSalesId() == null) {
			response.setMessage("NOT FOUND");
			response.setStatus(404);
			LOGGER.info("Invoice is not found");
		} else if (!Utilities.ignoreTime(submitedInvoice.getInvoiceDate()).equals(invoice.getInvoiceDate())) {
			response.setMessage("DATE NOT MATCHED");
			response.setStatus(411);
			LOGGER.info("Invoice date not matched");
		} else if (!submitedInvoice.getUserName().equals(invoice.getUserName())) {
			response.setMessage("USERNAME NOT MATCHED");
			response.setStatus(410);
			LOGGER.info("Invoice UserName not matched");
		} else if (!Utilities.compareStringLists(submitedInvoice.getCategories(), invoice.getCategories())) {
			response.setMessage("CATEGORIES OR NET SALE ARE NOT MATCHED");
			response.setStatus(412);
			LOGGER.info("Invoice Categories or Net Sale are not matched");
		} else {
			response.setMessage("SUCCESS");
			response.setStatus(200);
			LOGGER.info("Invoice matched");
		}
		return response;
	}

	private Invoice getPhilipsInvoice(String invoiceId) {
		String userName;
		Invoice invoice = new Invoice();
		PhilipsInvoice philipsInvoice;
		Optional<PhilipsInvoice> philipsInvoiceOptional = philipsInvoiceRepository.findById(invoiceId);
		if (philipsInvoiceOptional.isPresent()) {
			philipsInvoice = philipsInvoiceOptional.get();
			userName = philipsInvoice.getUser().getUserName();
			Users user = userRepository.findById(userName).get();
			List<PhilipsInvoiceCategories> categories = philipsInvoiceCategoriesRepository
					.findByPhilipsInvoice(philipsInvoice);
			invoice.setUserName(userName);
			invoice.setName(user.getName());
			invoice.setSalesId(philipsInvoice.getSalesId());
			invoice.setInvoiceDate(philipsInvoice.getInvoiceDate());
			invoice.setExtras(philipsInvoice.getExtras());
			List<String> categoryList = new ArrayList<>();
			for (PhilipsInvoiceCategories category : categories) {
				categoryList.add(category.getCategory().getCategoryName() + "," + category.getNetSale());
			}
			invoice.setCategories(categoryList);
			return invoice;
		}
		return invoice;
	}

}