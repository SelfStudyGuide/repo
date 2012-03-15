package org.ssg.gui.server.command;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public abstract class AbstractCommandTestCase<R extends Response, A extends Action<R>> {
	@Autowired
	private ActionHandlerLookup lookup;

	private ActionHandler<R, A> commandHandler;

	@Before
	public void initTest() {
		commandHandler = lookup.findHandler(testedCommandClass());
	}

	protected abstract Class<A> testedCommandClass();

	public R whenAction(A action) {
		return commandHandler.execute(action);
	}
}
