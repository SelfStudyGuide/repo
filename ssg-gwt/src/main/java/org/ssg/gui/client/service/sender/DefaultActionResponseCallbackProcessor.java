package org.ssg.gui.client.service.sender;

import static org.ssg.gui.client.service.res.SsgLookupMessagesHelper.dot2underscore;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DefaultActionResponseCallbackProcessor implements ActionResponseCallbackProcessor {

	private final ErrorDialog dialog;
	private final SsgMessages messages;
	private final SsgLookupMessages ssgLookupMessages;

	public DefaultActionResponseCallbackProcessor(ErrorDialog dialog, SsgMessages messages,
	        SsgLookupMessages ssgLookupMessages) {
		this.dialog = dialog;
		this.messages = messages;
		this.ssgLookupMessages = ssgLookupMessages;
	}

	public <R extends Response> AsyncCallback<R> processResponse(final Action<R> action,
	        final ActionResponseCallback<R> actionCallback) {

		return createGwtRpcCallback(action, actionCallback);
	}

	private <R extends Response> AsyncCallback<R> createGwtRpcCallback(final Action<R> action,
	        final ActionResponseCallback<R> actionCallback) {

		return new AsyncCallback<R>() {
			public void onFailure(Throwable ex) {
				handleException(action, ex, actionCallback);
			}

			public void onSuccess(R actionResponse) {
				actionCallback.onResponse(actionResponse);
			};
		};
	}

	protected <R extends Response> void displayError(Action<R> action, UnexpectedCommandException caught) {

		String dialogMsg = messages.serviceErrorUnexpected(caught.getMessage());
		dialog.show(dialogMsg, action.getActionName());
	}

	private <R extends Response> void handleException(final Action<R> action, Throwable ex,
	        ActionResponseCallback<R> actionCallback) {

		if (ex instanceof UnexpectedCommandException) {

			displayError(action, (UnexpectedCommandException) ex);
		} else if (ex instanceof SsgGuiServiceException) {

			SsgGuiServiceException guiEx = (SsgGuiServiceException) ex;
			actionCallback.onError(guiEx);
			if (!guiEx.isHandled()) {
				String dialogMsg = ssgLookupMessages.getString(dot2underscore(guiEx.getErrorCode()));
				dialog.show(dialogMsg, action.getActionName());
			}
		}
	}
}
