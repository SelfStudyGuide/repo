package org.ssg.gui.server.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.service.StudentService;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.server.command.ActionHandlerLookup;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml", "/serice-core-mock.ctx.xml",
        "/app-securty-mock.ctx.xml" })
public class StudentUIServiceTest {

	@Autowired
	private StudentUIService service;

	@Autowired
	private StudentService studentService;

	@Mock
	private ActionHandlerLookup actionHandlerLookupMock;

	@Mock
	private TstDataUtils.TestActionHandler actionHandlerMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void verifyThatActionIsHandled() {
		GetHomeworksResponse response = service.execute(new GetHomeworks(2));

		Assert.assertThat(response, notNullValue());

		verify(studentService).getHomeworks(2);
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
