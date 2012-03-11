package org.ssg.gui.server.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.service.StudentService;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml",
		"/serice-core-mock.ctx.xml" })
public class StudentUIServiceTest {

	@Autowired
	private StudentUIService service;

	@Autowired
	private StudentService studentService;

	@Test
	public void verifyThatActionIsHandled() {
		GetHomeworksResponse response = service.execute(new GetHomeworks(2));

		Assert.assertThat(response, notNullValue());

		verify(studentService).getHomeworks(2);
	}

	@Test(expected=UnexpectedCommandException.class)
	public void verifyThatExceptionIsThrowsIfHandlerForActionIsNotFound() {
		service.execute(new UnknownCommand());
	
	}

	private static class UnknownCommand implements Action<Response> {

		public String getActionName() {
			return null;
		}

	}

}
