package org.ssg.gui.client.service.sender;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.BaseAction;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionNameProvider;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.StudentControlServiceAsync;

import com.google.gwt.core.client.GWT;

public class DefaultActionSender implements ActionSender {

	private StudentControlServiceAsync service;
	private ActionNameProvider actionNameProvider;
	private ActionResponseCallbackProcessor responseProcessor;

	public DefaultActionSender(StudentControlServiceAsync service,
			ActionNameProvider actionNameProvider,
			ActionResponseCallbackProcessor processor) {
		this.service = service;
		this.actionNameProvider = actionNameProvider;
		this.responseProcessor = processor;
	}

	public <R extends Response> void send(Action<R> action,
			final ActionResponseCallback<R> callback) {

		if (action instanceof BaseAction<?>) {
			BaseAction<?> actionWithName = (BaseAction<?>) action;
			actionWithName.setActionName(getNameForAction(action));
		}

		if (callback instanceof ActionCallbackAdapter<?>) {
			ActionCallbackAdapter<R> callbackWithAction = (ActionCallbackAdapter<R>) callback;
			callbackWithAction.setAction(action);
		}

		doSend(action, callback);
	}

	private <R extends Response> void doSend(final Action<R> action,
			final ActionResponseCallback<R> callback) {

		if (!GWT.isProdMode()) {
			GWT.log("Sending action " + action.getActionName());
		}

		service.execute(action,
				responseProcessor.processResponse(action, callback));
	}

	private <R extends Response> String getNameForAction(Action<R> act) {
		return actionNameProvider.getActionName(act);
	}

}
