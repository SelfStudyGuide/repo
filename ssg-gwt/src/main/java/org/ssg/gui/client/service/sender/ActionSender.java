package org.ssg.gui.client.service.sender;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.ActionResponseCallback;

/**
 * Sender which performs sending user action request to the server. 
 * A callback instance should be passed into a {@link #send(Action, ActionResponseCallback)} method.
 */
public interface ActionSender {

	/**
	 * @param action user action
	 * @param callback response callback
	 */
	<R extends Response> void send(Action<R> action,
			ActionResponseCallback<R> callback);

}
