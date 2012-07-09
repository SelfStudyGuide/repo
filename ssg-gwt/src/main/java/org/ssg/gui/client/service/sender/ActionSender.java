package org.ssg.gui.client.service.sender;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.ActionResponseCallback;


public interface ActionSender {

	<R extends Response> void send(Action<R> action,
			ActionResponseCallback<R> callback);

}
