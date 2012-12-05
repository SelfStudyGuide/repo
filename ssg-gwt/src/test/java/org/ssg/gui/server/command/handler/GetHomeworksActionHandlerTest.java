package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.moduleBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.UserDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;

// Test for GetHomeworksActionHandler
public class GetHomeworksActionHandlerTest extends AbstractCommandTestCase<GetHomeworksResponse, GetHomeworks> {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CurriculumDao curriculumDao;

	private static final int STUDENT_ID = 1;
	private static final int UNK_STUDENT_ID = 2;
	private static final int HW_ID = 30;
	private static final int MODULE_ID = 40;

	private Student student;
	private Module module;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		Mockito.reset(userDao, curriculumDao);
		prepareData();
	}

	private void prepareData() {
		module = moduleBuilder().id(MODULE_ID).name("Module name").description("desc").build();
		Topic topic = topicBuilder().id(20).description("Description of topic").name("Test topic 1").build();
		student = TstDataUtils.createStudent("jd");
		TopicProgress progress = topicProgressBuilder().topic(topic).status("10").build();
		Homework homework = homeworkBuilder().id(HW_ID).module(module).topicProgress(progress).student(student).build();
	}

	@Test
	public void givenUnknownStudentIdWhenGetHomeworkActionThenException() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("studenthome.student.not.found")));
		
		// Given
		when(userDao.getStudentById(STUDENT_ID)).thenReturn(student);
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(module);
		GetHomeworks getHomeworks = new GetHomeworks(UNK_STUDENT_ID);

		// When
		GetHomeworksResponse response = whenAction(getHomeworks);
	}
	
	@Test
	public void givenUnfoundModuleWhenGetHomeworkActionThenException() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("studenthome.module.not.found")));
		
		// Given
		when(userDao.getStudentById(STUDENT_ID)).thenReturn(student);
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(null);
		GetHomeworks getHomeworks = new GetHomeworks(STUDENT_ID);

		// When
		GetHomeworksResponse response = whenAction(getHomeworks);
	}
	
	@Test
	public void givenStudentIdWhenGetHomeworkActionThenHomeworkInfoListShouldBeReturned() {
		// Given
		when(userDao.getStudentById(STUDENT_ID)).thenReturn(student);
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(module);
		GetHomeworks getHomeworks = new GetHomeworks(STUDENT_ID);

		// When
		GetHomeworksResponse response = whenAction(getHomeworks);

		// Then
		assertThat(response.getHomeworks().size(), is(1));
		HomeworkInfo homeworkInfo = response.getHomeworks().iterator().next();
		assertThat(homeworkInfo.getId(), is(30));
		assertThat(homeworkInfo.getAssignedModules(), is("Module name"));
	}

	@Override
	protected Class<GetHomeworks> testedCommandClass() {
		return GetHomeworks.class;
	}
}
