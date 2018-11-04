package com.philips.backend.dao;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-28
 */

@Entity
public class SubmitedInvoice {
	// private Integer id;
	private Date invoiceDate;
	private String extras;
	// EX : matched, failed, scheduled.
	private String status;
	private String salesId;

	private Set<SubmitedInvoiceCategories> submitedInvoiceCategories; // one2many
	private Users user;

	/**
	 * @param id
	 * @param invoiceDate
	 * @param extras
	 * @param status
	 */
	public SubmitedInvoice() {
		super();
	}

	/**
	 * @param invoiceDate
	 * @param extras
	 * @param status
	 */
	public SubmitedInvoice(Date invoiceDate, String extras, String salesId, String status) {
		super();
		this.salesId = salesId;
		this.invoiceDate = invoiceDate;
		this.extras = extras;
		this.status = status;
	}

	public SubmitedInvoice(Date invoiceDate, String extras, String salesId, String status,
			Set<SubmitedInvoiceCategories> submitedInvoiceCategories) {
		this(invoiceDate, extras, salesId, status);
		this.submitedInvoiceCategories = submitedInvoiceCategories;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	//// @Id
	//// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// public Integer getId() {
	// return id;
	// }
	//
	// /**
	// * @param id
	// * the id to set
	// */
	// public void setId(Integer id) {
	// this.id = id;
	// }

	/**
	 * @return the user
	 */

	@JsonIgnore
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
	 * @return the submitedInvoiceCategories
	 */
	@OneToMany(mappedBy = "philipsInvoice")
	public Set<SubmitedInvoiceCategories> getSubmitedInvoiceCategories() {
		return submitedInvoiceCategories;
	}

	/**
	 * @param submitedInvoiceCategories
	 *            the submitedInvoiceCategories to set
	 */
	public void setSubmitedInvoiceCategories(Set<SubmitedInvoiceCategories> submitedInvoiceCategories) {
		this.submitedInvoiceCategories = submitedInvoiceCategories;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubmitedInvoice [ invoiceDate=" + invoiceDate + ", extras=" + extras + ", status=" + status + "]";
	}

}