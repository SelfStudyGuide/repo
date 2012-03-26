package org.ssg.gui.client.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StudentControlServiceAsync {
	<T extends Response> void execute(Action<T> action,
			AsyncCallback<T> callback);
}
