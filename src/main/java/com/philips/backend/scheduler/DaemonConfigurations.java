package com.philips.backend.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DaemonConfigurations {

	@Value("${daemon.job.status.scheduled}")
	private String statusScheduled;
	@Value("${daemon.match.after}")
	private int matchAfter;

	/**
	 * 
	 */
	public DaemonConfigurations() {
		super();
	}

	/**
	 * @return the statusScheduled
	 */
	public String getStatusScheduled() {
		return statusScheduled;
	}

	/**
	 * @param statusScheduled
	 *            the statusScheduled to set
	 */
	public void setStatusScheduled(String statusScheduled) {
		this.statusScheduled = statusScheduled;
	}

	/**
	 * @return the matchAfter
	 */
	public int getMatchAfter() {
		return matchAfter * 1000 * 60;
	}

	/**
	 * @param matchAfter
	 *            the matchAfter to set
	 */
	public void setMatchAfter(int matchAfter) {
		this.matchAfter = matchAfter;
	}

}
