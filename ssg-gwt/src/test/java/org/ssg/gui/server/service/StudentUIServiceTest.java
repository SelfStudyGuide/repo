package org.ssg.gui.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ssg.gui.server.command.handler.AbstractCommandTestCase.hasErrorCode;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.support.TstDataUtils;
import org.ssg.core.support.TstDataUtils.TestActionResponse;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiSecurityException;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.server.command.ActionHandlerLookup;
import org.ssg.gui.server.command.handler.AbstractCommandTestCase;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.SsgSecurityException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml", "/serice-core-mock.ctx.xml",
        "/app-securty-mock.ctx.xml", "/test-gui-command.ctx.xml" })
public class StudentUIServiceTest {

	@Autowired
	private StudentUIService service;

	@Autowired(required = true)
	private Authorization authorization;

	@Mock
	private ActionHandlerLookup actionHandlerLookupMock;

	@Mock
	private TstDataUtils.TestActionHandler actionHandlerMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.reset(authorization);
	}

	@Test
	public void verifyThatActionIsHandled() {
		// Given
		TstDataUtils.TestAction action = new TstDataUtils.TestAction("test");

		// When
		TestActionResponse response = service.execute(action);

		// Then
		assertThat(response, notNullValue());
		assertThat(response.response, is("test"));
	}

	@Test
	public void givenActionWithCommandPreAuthorizeInterfaceWhenExecutingThenPreAuthorizeMethodShouldCall() {
		// Given
		TstDataUtils.TestAction action = new TstDataUtils.TestAction("test");

		// When
		service.execute(action);

		// Then
		verify(authorization).student();

	}

	@Test
	public void givenActionWithCommandPreAuthorizeInterfaceWhenExecutingThenSsgSecurityExceptionShouldBeWrappedWithSsgGui() {
		// Given
		TstDataUtils.TestAction action = new TstDataUtils.TestAction("test");
		doThrow(new SsgSecurityException("no luck")).when(authorization).student();

		thrown.expect(SsgGuiSecurityException.class);
		thrown.expectMessage("no luck");
		thrown.expect(hasErrorCode(is("security.accessdeny")));

		// When
		service.execute(action);

		// Then
		verify(authorization).student();

	}

	@Test(expected = UnexpectedCommandException.class)
	public void verifyThatExceptionIsThrowsIfHandlerForActionIsNotFound() {
		service.execute(new UnknownCommand());
	}

	@Test(expected = UnexpectedCommandException.class)
	public void verifyThatIfRuntimeExceptionIsWrappedWithUnexpectedCommandException() {
		StudentUIServiceImpl s = new StudentUIServiceImpl();
		s.setHandlerLookup(actionHandlerLookupMock);
		TstDataUtils.TestAction action = new TstDataUtils.TestAction();

		when(actionHandlerLookupMock.findHandler(same(TstDataUtils.TestAction.class))).thenReturn(actionHandlerMock);
		when(actionHandlerMock.execute(same(action))).thenThrow(new RuntimeException("Some unexpected"));

		s.execute(action);
	}

	private static class UnknownCommand implements Action<Response> {
		private static final long serialVersionUID = 1L;

		public String getActionName() {
			return null;
		}

	}

}
