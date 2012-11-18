package org.ssg.gui.server.command.handler;

import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.taskBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.TstDataBuilder.topicProgressBuilder;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.server.security.Authorization;

// GetTaskInfoActionHandler
public class GetTaskInfoActionHandlerTest extends AbstractCommandTestCase<GetTaskInfoResponse, GetTaskInfo> {

	private static final int HW_ID = 1;

	private static final int TASK_ID = 10;

	private static final int TOPIC_ID = 15;

	@Autowired
	private CurriculumDao curriculumDao;
	
	@Autowired
	private Authorization authorization;

	@Autowired
	private HomeworkDao homeworkDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		Mockito.reset(homeworkDao, curriculumDao, authorization);
	}
	
	@Test
	public void givenHomeworkAndTaskIdsThenTaksInfoShouldBeReturned() {
		// Given
		homework();
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);

		// When
		GetTaskInfoResponse response = whenAction(action);

		// Then
		TopicTaskInfo info = response.getTaskInfo();
		Assert.assertThat(info.getId(), CoreMatchers.is(10));
		Assert.assertThat(info.getExecrisesCount(), CoreMatchers.is(2));
		Assert.assertThat(info.getType(), CoreMatchers.is(TaskType.LEXICAL));
		Assert.assertThat(info.getCompletedCount(), CoreMatchers.is(0));
	}
	
	@Test
	public void givenHomeworkAndTaskIdsThenHomeworkIdShouldBeReturnedInTaskInfo() {
		// Given
		homework();
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);

		// When
		GetTaskInfoResponse response = whenAction(action);

		// Then
		TopicTaskInfo info = response.getTaskInfo();
		Assert.assertThat(info.getHomeworkId(), CoreMatchers.is(1));
	}

	@Test
	public void givenHomeworkAndTaskThenExerciseIdsShouldBeReturned() {
		// Given
		homework();
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);

		// When
		GetTaskInfoResponse response = whenAction(action);

		// Then
		Integer[] exerciseIds = response.getTaskInfo().getExerciseIds();
		assertThat(exerciseIds.length, is(2));
		assertThat(asList(exerciseIds), hasItem(valueOf(20)));
		assertThat(asList(exerciseIds), hasItem(valueOf(30)));
	}

	@Test
	public void givenTaskNotBelongsToHomeworkThenExceptionIsThrown() {
		// Given
		homeworkWithDiffernetTopic();
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);

		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.task.does.not.belong.to.hw")));

		// When
		whenAction(action);
	}
	
	@Test
	public void verifyThatExceptionIsThrownWhenHomeworkIsNotFound() {
		// Given
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);
		
		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.homework.notfound")));
		
		// When
		whenAction(action);
	}
	
	@Test
	public void verifyThatExceptionIsThrownWhenTaskIsNotFound() {
		// Given
		homework();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);
		
		// Then
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.task.notfound")));
		
		// When
		whenAction(action);
	}
	
	@Test
	public void givenHomeworkNotAssignedToStudentThenAuthorisationExceptionShouldBeThrown() {
		// Given
		Homework homework = homework();
		task();
		GetTaskInfo action = new GetTaskInfo(HW_ID, TASK_ID);

		// When
		whenAction(action);
		
		
		Mockito.verify(authorization).ownHomework(Mockito.same(homework));
	}
	
	private Homework homework() {
		Topic topic = topicBuilder().id(TOPIC_ID).build();
		TopicProgress progress = topicProgressBuilder().topic(topic).build();
		Homework homework = homeworkBuilder().id(1).topicProgress(progress).build();
		when(homeworkDao.getHomework(Mockito.eq(HW_ID))).thenReturn(homework);
		return homework;
	}

	private void homeworkWithDiffernetTopic() {
		Topic topic = topicBuilder().id(16).build();
		TopicProgress progress = topicProgressBuilder().topic(topic).build();
		Homework homework = homeworkBuilder().id(1).topicProgress(progress).build();
		when(homeworkDao.getHomework(Mockito.eq(HW_ID))).thenReturn(homework);
	}

	private void task() {
		Exercise e1 = TstDataBuilder.exerciseBuilder().id(20).description("d1").order(1).build();
		Exercise e2 = TstDataBuilder.exerciseBuilder().id(30).description("d2").order(2).build();
		Task task = taskBuilder().id(TASK_ID).name("Task1").description("Desc").execrisesCount(2)
		        .type(TaskType.LEXICAL).exercise(e1).exercise(e2).build();
		Topic topic = topicBuilder().task(task).id(TOPIC_ID).build();
		when(curriculumDao.getTask(Mockito.eq(TASK_ID))).thenReturn(task);
	}

	@Override
	protected Class<GetTaskInfo> testedCommandClass() {
		return GetTaskInfo.class;
	}

}
