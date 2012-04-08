package org.ssg.gui.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class DefaultWindowLocation implements WindowLocation {

	public void replace(String newURL) {
		String url = getUrl(newURL);
		if (GWT.isScript()) {
			//Window.alert("We are in development mode!");
			url += "?gwt.codesvr=127.0.0.1:9997";
		}
		Window.Location.replace(url);
	}

	public String getUrl(String page) {
		return GWT.getHostPageBaseURL() + page;
	}
}
