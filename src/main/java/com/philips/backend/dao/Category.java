package com.philips.backend.dao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-28
 */

@Entity
public class Category {
	private Integer id;
	private String categoryName;
	private String extras;
	
	Set<PhilipsInvoiceCategories> philipsInvoiceCategories; 
	Set<SubmitedInvoiceCategories> submitedInvoiceCategories; 

	public Category() {
		super();
	}

	/**
	 * @param categoryName
	 */
	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	/**
	 * @param categoryName
	 * @param extras
	 */
	public Category(String categoryName, String extras) {
		this(categoryName);
		this.extras = extras;
	}

	/**
	 * @param id
	 * @param categoryName
	 * @param extras
	 */
	public Category(Integer id, String categoryName, String extras) {
		this(categoryName, extras);
		this.id = id;
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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	 * @return the philipsInvoiceCategories
	 */
	@OneToMany(mappedBy = "category")
	public Set<PhilipsInvoiceCategories> getPhilipsInvoiceCategories() {
		return philipsInvoiceCategories;
	}

	/**
	 * @param philipsInvoiceCategories the philipsInvoiceCategories to set
	 */
	public void setPhilipsInvoiceCategories(Set<PhilipsInvoiceCategories> philipsInvoiceCategories) {
		this.philipsInvoiceCategories = philipsInvoiceCategories;
	}

	
	/**
	 * @return the submitedInvoiceCategories
	 */
	@OneToMany(mappedBy = "category")
	public Set<SubmitedInvoiceCategories> getSubmitedInvoiceCategories() {
		return submitedInvoiceCategories;
	}

	/**
	 * @param submitedInvoiceCategories the submitedInvoiceCategories to set
	 */
	public void setSubmitedInvoiceCategories(Set<SubmitedInvoiceCategories> submitedInvoiceCategories) {
		this.submitedInvoiceCategories = submitedInvoiceCategories;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", extras=" + extras
				+ ", philipsInvoiceCategories=" + philipsInvoiceCategories + ", submitedInvoiceCategories="
				+ submitedInvoiceCategories + "]";
	}

}