package org.ssg.gui.client.service;

import java.util.Date;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;


import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

public class DefaultActionNameProvider implements ActionNameProvider {

	private static final String ANONYMOUS = "Anonymous";
	private static DateTimeFormat format;

	static {
		if (GWT.isClient()) {
			// getFormat does not work in plain jvm
			format = DateTimeFormat.getFormat("yyyyMMdd-HHmmss");
		}
	}

	public <R extends Response> String getActionName(Action<R> action) {
		String username = UserInfoHolder.getAppUsername();

		if (username == null) {
			username = ANONYMOUS;
		}

		String dateStr = format == null ? new Date().toString() : format
				.format(new Date());
		String actionName = getActionClassName(action);
		String result = username + "/" + actionName + "/" + dateStr;
		return result;
	}

	private String getActionClassName(Action<?> action) {
		String className = action.getClass().getName();
		return className.substring(className.lastIndexOf(".") + 1);
	}

}
