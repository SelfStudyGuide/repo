package org.ssg.gui.server.command;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;

import static org.mockito.Mockito.*;

@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml",
		"/serice-core-mock.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GetHomeworksActionHandlerTest extends AbstractCommandTestCase<GetHomeworksResponse, GetHomeworks> {

	@Autowired
	private StudentService studentService;

	@Test
	public void verifyThatHandlerIsCreated() {
		when(studentService.getHomeworks(1)).thenReturn(homeworks());

		GetHomeworksResponse response = whenAction(new GetHomeworks(1));

		verify(studentService).getHomeworks(1);

		ArrayList<HomeworkInfo> homeworks = response.getHomeworks();
		Assert.assertThat(homeworks.size(), is(2));

	}

	private Collection<HomeworkInfo> homeworks() {
		ArrayList<HomeworkInfo> list = new ArrayList<HomeworkInfo>();

		HomeworkInfo info = new HomeworkInfo();
		list.add(info);
		info.setId(1);
		info = new HomeworkInfo();
		list.add(info);
		info.setId(2);

		return list;
	}

	@Override
	protected Class<GetHomeworks> testedCommandClass() {
		return GetHomeworks.class;
	}
}
