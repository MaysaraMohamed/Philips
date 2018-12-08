package com.philips.backend.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-10-28
 */

@Entity
public class PointsHistory {

	private Integer id;
	private double points;
	private double usedPoints;
	private Date pointsDate;
	private String extras; 
	
	
	private Users user;

	/*
	 */
	public PointsHistory() {
		super();
	}

	/**
	 * @param userName
	 * @param points
	 * @param usedPoints
	 * @param pointsDate
	 */
	public PointsHistory(double points, double usedPoints, Date pointsDate) {
		super();
		this.points = points;
		this.usedPoints = usedPoints;
		this.pointsDate = pointsDate;
	}

	/**
	 * @return the points
	 */
	public double getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(double points) {
		this.points = points;
	}

	/**
	 * @return the usedPoints
	 */
	public double getUsedPoints() {
		return usedPoints;
	}

	/**
	 * @param usedPoints
	 *            the usedPoints to set
	 */
	public void setUsedPoints(double usedPoints) {
		this.usedPoints = usedPoints;
	}

	/**
	 * @return the date
	 */
	// to use date only. 
	@Temporal(TemporalType.DATE)
	public Date getPointsDate() {
		return pointsDate;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setPointsDate(Date pointsDate) {
		this.pointsDate = pointsDate;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PointsHistory [id=" + id + ", points=" + points + ", usedPoints="
				+ usedPoints + ", pointsDate=" + pointsDate + "]";
	}

}