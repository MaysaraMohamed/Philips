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
 * @since 2018-10-28
 */

@Entity
public class SubCategory {

	private Integer id;
	private String subCategoryName;
	private String arSubCategoryName;
	private String extras;

	private Category category;
	
	public SubCategory() {
		super();
	}

	/**
	 * @param subCategoryName
	 */
	public SubCategory(String subCategoryName, String arSubCategoryName) {
		super();
		this.subCategoryName = subCategoryName;
		this.arSubCategoryName = arSubCategoryName;
	}

	/**
	 * @param subCategoryName
	 * @param extras
	 */
	public SubCategory(String subCategoryName, String extras, String arSubCategoryName) {
		this(subCategoryName, arSubCategoryName);
		this.extras = extras;
	}

	/**
	 * @param id
	 * @param subCategoryName
	 * @param extras
	 */
	public SubCategory(Integer id, String subCategoryName, String extras, String arSubCategoryName) {
		this(subCategoryName, extras, arSubCategoryName);
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
	 * @return the subCategoryName
	 */
	public String getSubCategoryName() {
		return subCategoryName;
	}

	/**
	 * @param subCategoryName
	 *            the subCategoryName to set
	 */
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	/**
	 * @return the arSubCategoryName
	 */
	public String getArSubCategoryName() {
		return arSubCategoryName;
	}

	/**
	 * @param arSubCategoryName
	 *            the arSubCategoryName to set
	 */
	public void setArSubCategoryName(String arSubCategoryName) {
		this.arSubCategoryName = arSubCategoryName;
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
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SubCategory [id=" + id + ", subCategoryName=" + subCategoryName + ", arSubCategoryName="
				+ arSubCategoryName + ", extras=" + extras + "]";
	}

}