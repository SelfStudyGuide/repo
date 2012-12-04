package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.moduleBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.StudentService;
import org.ssg.core.service.UserDao;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;

// Test for GetHomeworksActionHandler
public class GetHomeworksActionHandlerTest extends AbstractCommandTestCase<GetHomeworksResponse, GetHomeworks> {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserDao userDao;

	private static final int STUDENT_ID = 1;

	private static final int HW_ID = 30;

	private Student student;

	public void setUp() {
		prepareData();
		Mockito.when(userDao.getStudentById(STUDENT_ID)).thenReturn(student);
	}

	private void prepareData() {
		Module module = moduleBuilder().name("name").description("desc").build();
		Topic topic = topicBuilder().id(20).description("Description of topic").name("Test topic 1").build();
		student = TstDataUtils.createStudent("jd");
		TopicProgress progress = topicProgressBuilder().topic(topic).status("10").build();
		Homework homework = homeworkBuilder().id(HW_ID).module(module).topicProgress(progress).student(student).build();
	}

	@Test
	@Ignore
	public void givenStudentIdWhenGetHomeworkActionThenHomeworkInfoListShouldBeReturned() {
		// Given
		GetHomeworks getHomeworks = new GetHomeworks(STUDENT_ID);

		// When
		GetHomeworksResponse response = whenAction(getHomeworks);

		// Then
		assertThat(response.getHomeworks().size(), is(2));
	}

	@Test
	public void verifyThatHandlerIsCreated() {
		when(studentService.getHomeworks(1)).thenReturn(homeworks());

		GetHomeworksResponse response = whenAction(new GetHomeworks(1));

		verify(studentService).getHomeworks(1);

		ArrayList<HomeworkInfo> homeworks = response.getHomeworks();
		assertThat(homeworks.size(), is(2));

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
