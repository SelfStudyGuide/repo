package org.ssg.gui.client.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.sender.DefaultActionSender;

import com.google.gwt.core.client.GWT;

public abstract class ActionCallbackAdapter<R extends Response> implements ActionResponseCallback<R> {

	private Action<R> action;

	/**
	 * Underlying action is populated by {@link DefaultActionSender}.
	 */
	public void setAction(Action<R> action) {
		this.action = action;
	}

	public Action<R> getAction() {
		return action;
	}

	abstract public void onResponse(R response);

	public void onError(SsgGuiServiceException exception) {
		GWT.log("Uncatched exception ", exception);
	}
}
