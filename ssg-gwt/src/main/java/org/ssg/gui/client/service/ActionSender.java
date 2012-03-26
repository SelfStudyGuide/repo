package org.ssg.gui.client.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;


public interface ActionSender {

	<R extends Response> void send(Action<R> action,
			ActionResponseCallback<R> callback);

}
