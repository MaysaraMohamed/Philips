package com.philips.backend.common;

import java.util.List;

import com.philips.backend.dao.Gifts;

public class GiftsResponse {

	private double totalPoints;
	private String extras;
	private List<Gifts> gifts;

	/**
	 */
	public GiftsResponse() {
		super();
	}

	/**
	 * @param totalPoints
	 * @param extras
	 * @param gifts
	 */
	public GiftsResponse(double totalPoints, String extras, List<Gifts> gifts) {
		super();
		this.totalPoints = totalPoints;
		this.extras = extras;
		this.gifts = gifts;
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
	 * @return the totalPoints
	 */
	public double getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints
	 *            the totalPoints to set
	 */
	public void setTotalPoints(double totalPoints) {
		this.totalPoints = totalPoints;
	}

	/**
	 * @return the gifts
	 */
	public List<Gifts> getGifts() {
		return gifts;
	}

	/**
	 * @param gifts
	 *            the gifts to set
	 */
	public void setGifts(List<Gifts> gifts) {
		this.gifts = gifts;
	}
}
