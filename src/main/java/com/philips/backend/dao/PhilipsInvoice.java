package com.philips.backend.dao;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-28
 */

@Entity
public class PhilipsInvoice {

	private Date invoiceDate;
	private String extras;
	private String salesId;

	private Users user;
	private Set<PhilipsInvoiceCategories> philipsInvoiceCategories; // one2many

	public PhilipsInvoice() {
		super();
	}

	/**
	 * @param invoiceDate
	 * @param extras
	 */
	public PhilipsInvoice(Date invoiceDate, String extras, String salesId) {
		super();
		this.salesId = salesId;
		this.invoiceDate = invoiceDate;
		this.extras = extras;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
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
	 * @return the user
	 */
	@ManyToOne
	@JoinColumn(name = "user_name")
	public Users getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Users user) {
		this.user = user;
	}

	/**
	 * @return the salesId
	 */
	@Id
	public String getSalesId() {
		return salesId;
	}

	/**
	 * @param salesId
	 *            the salesId to set
	 */
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	/**
	 * @return the philipsInvoiceCategories
	 */
	@OneToMany(mappedBy = "philipsInvoice")
	public Set<PhilipsInvoiceCategories> getPhilipsInvoiceCategories() {
		return philipsInvoiceCategories;
	}

	/**
	 * @param philipsInvoiceCategories
	 *            the philipsInvoiceCategories to set
	 */
	public void setPhilipsInvoiceCategories(Set<PhilipsInvoiceCategories> philipsInvoiceCategories) {
		this.philipsInvoiceCategories = philipsInvoiceCategories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhilipsInvoice [invoiceDate=" + invoiceDate + ", extras=" + extras + ", salesId=" + salesId + ", user="
				+ user + ", philipsInvoiceCategories=" + philipsInvoiceCategories + "]";
	}
}