package org.ssg.gui.client.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StudentControlServiceAsync {
	<T extends Response> void execute(Action<T> action,
			AsyncCallback<T> callback);
}
