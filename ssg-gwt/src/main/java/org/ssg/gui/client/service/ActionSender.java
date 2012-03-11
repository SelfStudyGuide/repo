package org.ssg.gui.client.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface ActionSender {

	<R extends Response> void send(Action<R> action,
			ActionResponseCallback<R> callback);

}
