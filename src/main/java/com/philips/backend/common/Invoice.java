package com.philips.backend.common;

import java.util.Date;
import java.util.List;

public class Invoice {

	private String userName;
	private String name;
	private Date invoiceDate;
	private String salesId;
	private String extras;
	private List<String>categories;
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the salesId
	 */
	public String getSalesId() {
		return salesId;
	}
	/**
	 * @param salesId the salesId to set
	 */
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	/**
	 * @return the extras
	 */
	public String getExtras() {
		return extras;
	}
	/**
	 * @param extras the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}
	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<String> categories) {
		this.categories = categories;
	} 
	
	
}
