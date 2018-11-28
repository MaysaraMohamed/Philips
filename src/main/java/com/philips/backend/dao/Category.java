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
	private String categoryName ;
	private String arCategoryName ;
	private String extras ;
	
	Set<PhilipsInvoiceCategories> philipsInvoiceCategories; 
	Set<SubmitedInvoiceCategories> submitedInvoiceCategories; 
	
	Set<SubCategory> subCategories; 

	public Category() {
		super();
	}

	/**
	 * @param categoryName
	 */
	public Category(String categoryName, String arCategoryName) {
		super();
		this.categoryName = categoryName;
		this.arCategoryName = arCategoryName; 
	}

	/**
	 * @param categoryName
	 * @param extras
	 */
	public Category(String categoryName, String extras, String arCategoryName) {
		this(categoryName, arCategoryName);
		this.extras = extras;
	}

	/**
	 * @param id
	 * @param categoryName
	 * @param extras
	 */
	public Category(Integer id, String categoryName, String extras, String arCategoryName) {
		this(categoryName, extras, arCategoryName);
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
	

	/**
	 * @return the subCategories
	 */
	@OneToMany(mappedBy = "category")
	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories the subCategories to set
	 */
	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	/**
	 * @return the arCategoryName
	 */
	public String getArCategoryName() {
		return arCategoryName;
	}

	/**
	 * @param arCategoryName the arCategoryName to set
	 */
	public void setArCategoryName(String arCategoryName) {
		this.arCategoryName = arCategoryName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", arCategoryName=" + arCategoryName
				+ ", extras=" + extras + ", philipsInvoiceCategories=" + philipsInvoiceCategories
				+ ", submitedInvoiceCategories=" + submitedInvoiceCategories + "]";
	}

}