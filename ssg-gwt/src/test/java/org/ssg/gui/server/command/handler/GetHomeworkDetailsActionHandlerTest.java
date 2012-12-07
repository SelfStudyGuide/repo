package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.moduleBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.service.StudentService;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;

// GetHomeworkDetailsActionHandler
public class GetHomeworkDetailsActionHandlerTest extends
        AbstractCommandTestCase<GetHomeworkDetailsResponse, GetHomeworkDetails> {

	private static final int STUDENT_ID = 1;
	private static final int UNK_STUDENT_ID = 2;
	private static final int HW_ID = 30;
	private static final int MODULE_ID = 40;
	
	@Autowired
	private StudentService service;
	private Module module;
	private Student student;
	private Homework homework;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private CurriculumDao curriculumDao;

	@Override
	protected Class<GetHomeworkDetails> testedCommandClass() {
		return GetHomeworkDetails.class;
	}

	@Before
	public void setUp() {
		Mockito.reset(homeworkDao, curriculumDao);
		prepareData();
	}

	private void prepareData() {
		module = moduleBuilder().id(MODULE_ID).name("Module name").description("desc").build();
		Topic topic = topicBuilder().id(20).description("Description of topic").name("Test topic 1").build();
		student = TstDataUtils.createStudent("jd");
		TopicProgress progress = topicProgressBuilder().topic(topic).status("10").build();
		homework = homeworkBuilder().id(HW_ID).module(module).topicProgress(progress).student(student).build();
	}

	@Test
	public void givenGetHomeworkDetailsActionThenHomeworkInfoIsReturned() {
		// Given
		when(homeworkDao.getHomework(HW_ID)).thenReturn(homework);
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(module);
		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(STUDENT_ID, HW_ID);

		// When
		GetHomeworkDetailsResponse response = whenAction(getHomeworkDetails);

		// Then
		assertThat(response.getHomework(), notNullValue());
		assertThat(response.getHomework().getId(), CoreMatchers.is(30));
		assertThat(response.getHomework().getAssignedModules(), CoreMatchers.is("Module name"));
	}

	@Test
	public void givenGetHomeworkDetailsActionThenTopicProgressInfoIsReturned() {
		// Given
		when(homeworkDao.getHomework(HW_ID)).thenReturn(homework);
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(module);
		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(STUDENT_ID, HW_ID);

		// When
		GetHomeworkDetailsResponse response = whenAction(getHomeworkDetails);

		// Then
		assertThat(response.getHomework(), notNullValue());
		ArrayList<TopicProgressInfo> topicProgress = response.getHomework().getTopicProgress();
		assertThat(topicProgress, notNullValue());
		assertThat(topicProgress.get(0).getTopicId(), is(20));
		assertThat(topicProgress.get(0).getTopicName(), is("Test topic 1"));
		assertThat(topicProgress.get(0).getStatus(), is("10"));
	}
	
	@Test
	public void whenHomeworkNowFoundInDbThenExceptionIsThrown() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(CoreMatchers.is("studenthome.homework.not.found")));
		
		// Given
		when(curriculumDao.getModuleById(MODULE_ID)).thenReturn(module);
		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(STUDENT_ID, HW_ID);
		
		// When
		GetHomeworkDetailsResponse response = whenAction(getHomeworkDetails);
	}
	
	@Test
	public void whenModuleNowFoundInDbThenExceptionIsThrown() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(CoreMatchers.is("studenthome.module.not.found")));
		
		// Given
		when(homeworkDao.getHomework(HW_ID)).thenReturn(homework);
		GetHomeworkDetails getHomeworkDetails = new GetHomeworkDetails(STUDENT_ID, HW_ID);
		
		// When
		GetHomeworkDetailsResponse response = whenAction(getHomeworkDetails);
	}

}
