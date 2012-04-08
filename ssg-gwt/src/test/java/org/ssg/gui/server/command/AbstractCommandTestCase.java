package org.ssg.gui.server.command;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;

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
		Assert.assertThat("Handler for command " + testedCommandClass() + " is not found", commandHandler, CoreMatchers.notNullValue());
		return commandHandler.execute(action);
	}
}
