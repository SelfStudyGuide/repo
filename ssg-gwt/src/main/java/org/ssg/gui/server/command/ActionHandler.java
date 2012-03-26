package org.ssg.gui.server.command;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiServiceException;

public interface ActionHandler<R extends Response, A extends Action<R>> {
	R execute(A action) throws SsgGuiServiceException;

	Class<A> forClass();
}
