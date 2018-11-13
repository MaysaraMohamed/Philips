package com.philips.backend.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-06
 */

@Entity
public class Gifts implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;


	private Integer id;
	private String extras; 
	private double points; 
	private String giftOptions; 
	private String arGiftOptions; 
	private String userType;
	
	
	
	
	/**
	 */
	public Gifts() {
		super();
	}
	/**
	 * @param id
	 * @param extras
	 * @param points
	 * @param giftOptions
	 * @param arGiftOptions
	 * @param userType
	 */
	public Gifts(Integer id, String extras, double points, String giftOptions, String arGiftOptions, String userType) {
		super();
		this.id = id;
		this.extras = extras;
		this.points = points;
		this.giftOptions = giftOptions;
		this.arGiftOptions = arGiftOptions;
		this.userType = userType;
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
	 * @param id the id to set
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
	 * @param extras the extras to set
	 */
	public void setExtras(String extras) {
		this.extras = extras;
	}
	/**
	 * @return the points
	 */
	public double getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return the giftOptions
	 */
	public String getGiftOptions() {
		return giftOptions;
	}
	/**
	 * @param giftOptions the giftOptions to set
	 */
	public void setGiftOptions(String giftOptions) {
		this.giftOptions = giftOptions;
	}
	/**
	 * @return the arGiftOptions
	 */
	public String getArGiftOptions() {
		return arGiftOptions;
	}
	/**
	 * @param arGiftOptions the arGiftOptions to set
	 */
	public void setArGiftOptions(String arGiftOptions) {
		this.arGiftOptions = arGiftOptions;
	}
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Gifts [id=" + id + ", extras=" + extras + ", points=" + points + ", giftOptions=" + giftOptions
				+ ", arGiftOptions=" + arGiftOptions + ", userType=" + userType + "]";
	} 
}