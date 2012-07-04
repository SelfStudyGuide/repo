package org.ssg.gui.server.command;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiServiceException;

public interface ActionHandler<R extends Response, A extends Action<R>> {
	R execute(A action) throws SsgGuiServiceException;

	/**
	 * Returns class of an action for which this handler should be registered.
	 */
	Class<A> forClass();
}
