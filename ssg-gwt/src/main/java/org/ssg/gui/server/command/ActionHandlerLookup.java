package org.ssg.gui.server.command;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;


public interface ActionHandlerLookup {
	<R extends Response, A extends Action<R>> ActionHandler<R, A> findHandler(Class<A> actionClass);
}
