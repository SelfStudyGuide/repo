package org.ssg.gui.client.service.sender;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.ActionResponseCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionResponseCallbackProcessor {

	public <R extends Response> AsyncCallback<R> processResponse(Action<R> action,
	        ActionResponseCallback<R> actionCallback);
}
