package com.philips.backend.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-29
 */

@Entity
public class SubmitedInvoiceCategories {

	// @EmbeddedId
	// private int invoiceCategoryIdentity;
	private Integer id;
	private double netSale;
	private String extras;

	private PhilipsInvoice philipsInvoice;
	private Category category;

	/**
	 * @param categoryName
	 */
	public SubmitedInvoiceCategories() {
		super();
	}

	/**
	 * @param categoryName
	 * @param extras
	 */
	public SubmitedInvoiceCategories(String extras, double netSale) {
		this();
		this.extras = extras;
		this.netSale = netSale;
	}

	/**
	 * @param id
	 * @param categoryName
	 * @param extras
	 */
	public SubmitedInvoiceCategories(String extras, double netSale, InvoiceCategoryIdentity invoiceCategoryIdentity) {
		this(extras, netSale);
		// this.invoiceCategoryIdentity = invoiceCategoryIdentity;
	}

	/**
	 * @return the extras
	 */
	public String getExtras() {
		return extras;
	}

	/**
	 * @param extras
	 *            the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}

	/**
	 * @return the philipsInvoice
	 */
	@ManyToOne
	@JoinColumn(name = "sales_id")
	public PhilipsInvoice getPhilipsInvoice() {
		return philipsInvoice;
	}

	/**
	 * @param philipsInvoice
	 *            the philipsInvoice to set
	 */
	public void setPhilipsInvoice(PhilipsInvoice philipsInvoice) {
		this.philipsInvoice = philipsInvoice;
	}

	/**
	 * @return the category
	 */
	@ManyToOne
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	// /**
	// * @return the invoiceCategoryIdentity
	// */
	// public InvoiceCategoryIdentity getInvoiceCategoryIdentity() {
	// return invoiceCategoryIdentity;
	// }
	//
	// /**
	// * @param invoiceCategoryIdentity
	// * the invoiceCategoryIdentity to set
	// */
	// public void setInvoiceCategoryIdentity(InvoiceCategoryIdentity
	// invoiceCategoryIdentity) {
	// this.invoiceCategoryIdentity = invoiceCategoryIdentity;
	// }

	/**
	 * @return the netSale
	 */
	public double getNetSale() {
		return netSale;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param netSale
	 *            the netSale to set
	 */
	public void setNetSale(double netSale) {
		this.netSale = netSale;
	}

}