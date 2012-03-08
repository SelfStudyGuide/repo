package org.ssg.gui.server.command;

import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface ActionHandler<R extends Response, A extends Action<R>> {
	R execute(A action) throws SsgGuiServiceException;

	Class<A> forClass();
}
