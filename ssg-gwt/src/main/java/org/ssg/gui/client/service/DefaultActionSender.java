package org.ssg.gui.client.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.BaseAction;
import org.ssg.gui.shared.Response;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultActionSender implements ActionSender {

	private StudentControlServiceAsync service;
	private ActionNameProvider actionNameProvider;

	protected DefaultActionSender() {
	}

	public DefaultActionSender(StudentControlServiceAsync service,
			ActionNameProvider actionNameProvider) {
		this.service = service;
		this.actionNameProvider = actionNameProvider;
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

	private <R extends Response> void doSend(Action<R> action,
			final ActionResponseCallback<R> callback) {

		if (!GWT.isProdMode()) {
			GWT.log("Sending action " + action.getActionName());
		}

		service.execute(action, new AsyncCallback<R>() {

			public void onFailure(Throwable caught) {
				if (caught instanceof SsgGuiServiceException) {
					callback.onError((SsgGuiServiceException) caught);
				} else {
					GWT.log("unexpected excpeptio", caught);
				}
			}

			public void onSuccess(R response) {
				callback.onResponse(response);
			}
		});
	}

	private <R extends Response> String getNameForAction(Action<R> act) {
		return actionNameProvider.getActionName(act);
	}

	public StudentControlServiceAsync getService() {
		return service;
	}

	public void setService(StudentControlServiceAsync service) {
		this.service = service;
	}
}
