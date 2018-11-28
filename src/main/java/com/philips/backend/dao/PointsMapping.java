package com.philips.backend.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PointsMapping {
	private Integer netSale; 
	private Integer points; 
	private Integer id;
	
	
	
	/**
	 * @param netSale
	 * @param points
	 * @param id
	 */
	public PointsMapping() {
		super();
	}
	
	
	/**
	 * @param netSale
	 * @param points
	 * @param id
	 */
	public PointsMapping(Integer netSale, Integer points, Integer id) {
		super();
		this.netSale = netSale;
		this.points = points;
		this.id = id;
	}
	/**
	 * @return the netSale
	 */
	public Integer getNetSale() {
		return netSale;
	}
	/**
	 * @param netSale the netSale to set
	 */
	public void setNetSale(Integer netSale) {
		this.netSale = netSale;
	}
	/**
	 * @return the points
	 */
	public Integer getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(Integer points) {
		this.points = points;
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
	
	

}