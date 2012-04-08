package org.ssg.gui.client.service;


public interface WindowLocation {
	/**
	 * @param newURL url relatively to application context
	 */
	void replace(String newURL);
	
	/**
	 * Create url with prefix of the hosting page (GWT.getHostPageBaseURL()) + specific page.
	 */
	String getUrl(String page);
}
