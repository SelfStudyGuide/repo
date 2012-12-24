package org.ssg.gui.client.service.sender;

import static org.ssg.gui.client.service.res.SsgLookupMessagesHelper.dot2underscore;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.SsgGuiSecurityException;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;

import com.google.gwt.core.client.GWT;
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
				GWT.log("Process failure " + ex.getMessage() + " for action " + action.getActionName());
				handleException(action, ex, actionCallback);
			}

			public void onSuccess(R actionResponse) {
				GWT.log("Process success repospone " + actionResponse + " for action " + action.getActionName());
				actionCallback.onResponse(actionResponse);
			};
		};
	}

	protected <R extends Response> void handleUnexpectedEx(UnexpectedCommandException caught, Action<R> action) {

		String dialogMsg = messages.serviceErrorUnexpected(caught.getMessage());
		dialog.show(dialogMsg, action.getActionName());
	}

	private <R extends Response> void handleException(final Action<R> action, Throwable ex,
	        ActionResponseCallback<R> actionCallback) {

		if (ex instanceof UnexpectedCommandException) {

			handleUnexpectedEx((UnexpectedCommandException) ex, action);
		} else if (ex instanceof SsgGuiServiceException) {

			SsgGuiServiceException guiEx = (SsgGuiServiceException) ex;
			actionCallback.onError(guiEx);
			if (!guiEx.isHandled()) {
				if (guiEx instanceof SsgGuiSecurityException) {
					handleSecurityEx((SsgGuiSecurityException) guiEx, action);
				} else {
					handleServiceEx(guiEx, action);
				}
			}
		}
	}

	protected <R extends Response> void handleServiceEx(SsgGuiServiceException guiEx, final Action<R> action) {

		String dialogMsg = ssgLookupMessages.getString(dot2underscore(guiEx.getErrorCode()));
		dialog.show(dialogMsg, action.getActionName());
	}

	protected <R extends Response> void handleSecurityEx(SsgGuiSecurityException secEx, final Action<R> action) {

		String dialogMsg = ssgLookupMessages.getString(dot2underscore(secEx.getErrorCode()));
		dialogMsg += "\n" + secEx.getMessage();
		dialog.show(dialogMsg, action.getActionName());
	}
}
