package org.ssg.gui.server.command.handler;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.command.ActionHandlerLookup;

/**
 * Base class for action handler tests. It contains some generic methods. <br/>
 * Use: <br/>
 * Action a = new Action(5);<br/>
 * Response r = whenAction(a);
 */
@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml", "/serice-core-mock.ctx.xml", "/app-securty-mock.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractCommandTestCase<R extends Response, A extends Action<R>> {

	@Autowired
	private ActionHandlerLookup lookup;

	private ActionHandler<R, A> commandHandler;

	@Before
	public void initTest() {
		assertThat("ActionHandlerLookup is not initialised.", lookup, CoreMatchers.notNullValue());
		commandHandler = lookup.findHandler(testedCommandClass());
	}

	protected abstract Class<A> testedCommandClass();

	public R whenAction(A action) {
		Assert.assertThat("Handler for command " + testedCommandClass() + " is not found", commandHandler,
		        CoreMatchers.notNullValue());
		return commandHandler.execute(action);
	}

	public static Matcher<SsgGuiServiceException> hasErrorCode(final Matcher<String> matcher) {
		return new TypeSafeMatcher<SsgGuiServiceException>() {
			public void describeTo(Description description) {
				description.appendText("exception with error code ");
				description.appendDescriptionOf(matcher);
			}

			@Override
			public boolean matchesSafely(SsgGuiServiceException item) {
				return matcher.matches(item.getErrorCode());
			}
		};
	}
}
