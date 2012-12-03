package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.ssg.core.dto.TaskType.LEXICAL;
import static org.ssg.core.dto.TaskType.LISTENING;
import static org.ssg.core.dto.TaskType.TEXT;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.taskBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;
import org.ssg.gui.server.security.Authorization;

/**
 * Test of {@link GetTopicTasksInfoActionHandler}
 */
public class GetTopicTasksInfoActionHandlerTest extends
        AbstractCommandTestCase<GetTopicTasksInfoResponse, GetTopicTasksInfo> {

	private static final int TOPIC_ID = 2;

	private static final int HW_ID = 1;

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private Authorization authorization;
	
	@Autowired
	private CurriculumDao curriculumDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Module module;

	private Homework homework;

	private Student student;

    private Topic topic;

	@Before
	public void setUp() {
		createTestData();
		when(homeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(homework);
        when(curriculumDao.getTopic(TOPIC_ID)).thenReturn(topic);
	}

	@Test
	public void verifyThatCollectionOfTaskInfoIsReturnedWithReponse() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);

		// When
		GetTopicTasksInfoResponse response = whenAction(action);

		// Then
		assertThat(response.getTaskInfos(), hasSize(3));
	}

	@Test
	public void verifyThatListeningTaskIsPopulatedCorrectly() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);

		// When
		GetTopicTasksInfoResponse response = whenAction(action);

		// Then
		TopicTaskInfo task = lookForTask(response.getTaskInfos(), LISTENING);
		assertThat(task.getId(), is(5));
		assertThat(task.getCompletedCount(), is(0));
		assertThat(task.getExecrisesCount(), is(10));
		assertThat(task.getType(), is(LISTENING));
	}

	@Test
	public void verifyThatTextTaskIsPopulatedCorrectly() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);

		// When
		GetTopicTasksInfoResponse response = whenAction(action);

		// Then
		TopicTaskInfo task = lookForTask(response.getTaskInfos(), TEXT);
		assertThat(task.getId(), is(6));
		assertThat(task.getCompletedCount(), is(0));
		assertThat(task.getExecrisesCount(), is(4));
		assertThat(task.getType(), is(TEXT));
	}

	@Test
	public void verifyThatLexicalTaskIsPopulatedCorrectly() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);

		// When
		GetTopicTasksInfoResponse response = whenAction(action);

		// Then
		TopicTaskInfo task = lookForTask(response.getTaskInfos(), LEXICAL);
		assertThat(task.getId(), is(7));
		assertThat(task.getCompletedCount(), is(0));
		assertThat(task.getExecrisesCount(), is(2));
		assertThat(task.getType(), is(LEXICAL));
	}

	@Test
	public void verifyThatIfHomeworkIsNotFoundThenExceptionIsThrown() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);
		returnNullWhenHomeworkIsRequested();
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));

		// When
		GetTopicTasksInfoResponse response = whenAction(action);
	}

	@Test
	public void verifyThatIfTopicIsNotFoundThenExceptionIsThrown() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);
		testDataWithoutTopics();
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("topic.view.notfound")));

		// When
		GetTopicTasksInfoResponse response = whenAction(action);
	}

	@Test
	public void verifyThenOwnHomeworkCalledWhenGetTopicTaskInfoRequestIsProcessed() {
		// Given
		GetTopicTasksInfo action = new GetTopicTasksInfo(HW_ID, TOPIC_ID);
		when(homeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(homework);

		// When
		whenAction(action);

		// Then
		verify(authorization).ownHomework(homework);
	}

	private void returnNullWhenHomeworkIsRequested() {
		when(homeworkDao.getHomework(1)).thenReturn(null);
	}

	private void testDataWithoutTopics() {
		Module module = TstDataUtils.createModule();

		Homework homework = homeworkBuilder().id(HW_ID).student(TstDataUtils.createStudent("foo")).module(module)
		        .build();

		when(homeworkDao.getHomework(Matchers.eq(HW_ID))).thenReturn(homework);
	}

	private void createTestData() {
		module = TstDataUtils.createModule();

		Task listeningTask = taskBuilder().id(5).type(LISTENING).execrisesCount(10).build();

		Task textTask = taskBuilder().id(6).type(TEXT).execrisesCount(4).build();

		Task lexicalTask = taskBuilder().id(7).type(LEXICAL).execrisesCount(2).build();

		topic = topicBuilder().module(module).id(TOPIC_ID).name("topic1").task(listeningTask).task(lexicalTask)
		        .task(textTask).build();

		TopicProgress topicProgress = topicProgressBuilder().topic(topic).build();

		student = TstDataUtils.createStudent("John");
		homework = homeworkBuilder().id(HW_ID).student(student).module(module).topicProgress(topicProgress).build();

	}

	private TopicTaskInfo lookForTask(List<TopicTaskInfo> taskInfos, TaskType type) {

		for (TopicTaskInfo task : taskInfos) {

			if (task.isType(type)) {
				return task;
			}
		}
		return null;
	}

	@Override
	protected Class<GetTopicTasksInfo> testedCommandClass() {
		return GetTopicTasksInfo.class;
	}
}
