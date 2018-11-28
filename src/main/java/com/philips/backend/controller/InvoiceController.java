package com.philips.backend.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.philips.backend.dao.SubmitedInvoice;
import com.philips.backend.dao.SubmitedInvoiceCategories;
import com.philips.backend.dao.Users;
import com.philips.backend.repository.CategoryRepository;
import com.philips.backend.repository.PhilipsInvoiceCategoriesRepository;
import com.philips.backend.repository.PhilipsInvoiceRepository;
import com.philips.backend.repository.PointsHistoryRepository;
import com.philips.backend.repository.SubmitedInvoiceCategoriesRepository;
import com.philips.backend.repository.SubmitedInvoiceRepository;
import com.philips.backend.repository.UserRepository;

@RestController
public class InvoiceController {

	@Autowired
	private PhilipsInvoiceCategoriesRepository philipsInvoiceCategoriesRepository;

	@Autowired
	private PhilipsInvoiceRepository philipsInvoiceRepository;

	@Autowired
	private SubmitedInvoiceRepository submitedInvoiceRepository;

	@Autowired
	private SubmitedInvoiceCategoriesRepository submitedInvoiceCategoriesRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PointsHistoryRepository pointsHistoryRepository;
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

	public Response matchInvoice(String invoiceId) {
		Response response = new Response();
		Invoice submitedInvoice = getSubmitedInvoice(invoiceId);
		Invoice invoice = getPhilipsInvoice(invoiceId);
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

	@RequestMapping(method = RequestMethod.POST, value = "/submitInvoice")
	public Response submitInvoice(@RequestBody Invoice invoice) {
		Response response = new Response();
		// To prevent user from submitting invoice that is already matched and he gain its points. 
		if(submitedInvoiceRepository.findById(invoice.getSalesId()).isPresent())
		{
			if (submitedInvoiceRepository.findById(invoice.getSalesId()).get().getStatus().equals("200") )
			{
				response.setStatus(403);
				response.setMessage("Invoice already compensated");
				return response; 
			}
		}
		SubmitedInvoice submitedInvoice = new SubmitedInvoice();
		submitedInvoice.setSalesId(invoice.getSalesId());
		submitedInvoice.setUser(userRepository.findByUserName(invoice.getUserName()).get(0));
		submitedInvoice.setExtras(invoice.getExtras());
		submitedInvoice.setInvoiceDate(invoice.getInvoiceDate());
		submitedInvoice.setSubmissionDate(new Date());
		submitedInvoice.setStatus("SCHEDULED");
		submitedInvoiceRepository.save(submitedInvoice);
		List<String> categories = invoice.getCategories();
		if (submitedInvoiceCategoriesRepository.findBySubmitedInvoice(submitedInvoice).size() > 0)
			submitedInvoiceCategoriesRepository.deleteBySubmitedInvoice(submitedInvoice);
		for (String category : categories) {
			String categoryName = category.split(",")[0];
			double netSale = Double.parseDouble(category.split(",")[1]);
			SubmitedInvoiceCategories submitedInvoiceCategories = new SubmitedInvoiceCategories();
			if(categoryRepository.findByCategoryName(categoryName) != null) {
				submitedInvoiceCategories.setCategory(categoryRepository.findByCategoryName(categoryName));
			}else {
				submitedInvoiceCategories.setCategory(categoryRepository.findByArCategoryName(categoryName));
			}
			submitedInvoiceCategories.setNetSale(netSale);
			submitedInvoiceCategories.setSubmitedInvoice(submitedInvoice);
			submitedInvoiceCategoriesRepository.save(submitedInvoiceCategories);
		}
		response.setStatus(200);
		response.setMessage("SUCCESS");
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
			// Update to match Arabic or English invoices
			for (PhilipsInvoiceCategories category : categories) {
				if(category.getCategory().getCategoryName() != null) {
					categoryList.add(category.getCategory().getCategoryName() + "," + category.getNetSale());
				}else if(category.getCategory().getArCategoryName() != null) {
					categoryList.add(category.getCategory().getArCategoryName() + "," + category.getNetSale());
				}
			}
			invoice.setCategories(categoryList);
			return invoice;
		}
		return invoice;
	}

	private Invoice getSubmitedInvoice(String invoiceId) {
		String userName;
		Invoice invoice = new Invoice();
		SubmitedInvoice submitedInvoice;
		Optional<SubmitedInvoice> submitedInvoiceOptional = submitedInvoiceRepository.findById(invoiceId);
		if (submitedInvoiceOptional.isPresent()) {
			submitedInvoice = submitedInvoiceOptional.get();
			userName = submitedInvoice.getUser().getUserName();
			Users user = userRepository.findById(userName).get();
			List<SubmitedInvoiceCategories> categories = submitedInvoiceCategoriesRepository
					.findBySubmitedInvoice(submitedInvoice);
			invoice.setUserName(userName);
			invoice.setName(user.getName());
			invoice.setSalesId(submitedInvoice.getSalesId());
			invoice.setInvoiceDate(submitedInvoice.getInvoiceDate());
			invoice.setExtras(submitedInvoice.getExtras());
			List<String> categoryList = new ArrayList<>();
			// Update to match Arabic or English invoices 
			for (SubmitedInvoiceCategories category : categories) {
				if(category.getCategory().getCategoryName() != null) {
					categoryList.add(category.getCategory().getCategoryName() + "," + category.getNetSale());
				}else if(category.getCategory().getArCategoryName() != null) {
					categoryList.add(category.getCategory().getArCategoryName() + "," + category.getNetSale());
				}
			}
			invoice.setCategories(categoryList);
			return invoice;
		}
		return invoice;
	}

}