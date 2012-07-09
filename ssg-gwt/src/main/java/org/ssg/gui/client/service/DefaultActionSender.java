package org.ssg.gui.client.service;

import static org.ssg.gui.client.service.res.SsgLookupMessagesHelper.dot2underscore;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.BaseAction;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultActionSender implements ActionSender {

	private StudentControlServiceAsync service;
	private ActionNameProvider actionNameProvider;
	private ErrorDialog errorDialog;
	private SsgMessages messages;
	private SsgLookupMessages ssgLookupMessages;

	protected DefaultActionSender() {
	}

	public DefaultActionSender(StudentControlServiceAsync service,
			ActionNameProvider actionNameProvider, ErrorDialog errorDialog, SsgMessages messages) {
		this.service = service;
		this.actionNameProvider = actionNameProvider;
		this.errorDialog = errorDialog;
		this.messages = messages;
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

		service.execute(action, new AsyncCallback<R>() {

			public void onFailure(Throwable caught) {
				if (caught instanceof UnexpectedCommandException) {
					displayError(action, (UnexpectedCommandException)caught);
				} else if (caught instanceof SsgGuiServiceException) {
					SsgGuiServiceException guiEx = (SsgGuiServiceException) caught;
					callback.onError(guiEx);
					if (!guiEx.isHandled()) {
						String dialogMsg = ssgLookupMessages
								.getString(dot2underscore(guiEx.getErrorCode()));
						errorDialog.show(dialogMsg, action);
					}
				} else {
					GWT.log("unexpected exception", caught);
				}
			}

			public void onSuccess(R response) {
				callback.onResponse(response);
			}
		});
	}

	protected void displayError(Action<?> action, UnexpectedCommandException caught) {
		String dialogMsg = messages.serviceErrorUnexpected(caught.getMessage());
		errorDialog.show(dialogMsg, action);
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

	public SsgLookupMessages getSsgLookupMessages() {
		return ssgLookupMessages;
	}

	public void setSsgLookupMessages(SsgLookupMessages ssgLookupMessages) {
		this.ssgLookupMessages = ssgLookupMessages;
	}
	
	
}
