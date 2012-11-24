package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataBuilder.homeworkBuilder;
import static org.ssg.core.support.TstDataBuilder.taskBuilder;
import static org.ssg.core.support.TstDataBuilder.topicBuilder;
import static org.ssg.core.support.databuilder.ExerciseBuilder.exerciseBuilder;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.domain.Exercise;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.ExerciseType;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.task.action.GetExerciseInfo;
import org.ssg.gui.client.task.action.GetExerciseInfoResponse;

// GetExerciseInfoActionHandler
public class GetExerciseInfoActionHandlerTest extends AbstractCommandTestCase<GetExerciseInfoResponse, GetExerciseInfo> {

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired
	private HomeworkDao homeworkDao;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		// Sunny day data
		Exercise e = exerciseBuilder().id(20).exerciseType(ExerciseType.GENERIC).build();
		Task task = taskBuilder().id(40).exercise(e).build();
		Topic topic = topicBuilder().id(30).task(task).build();

		when(curriculumDao.getExercise(eq(20))).thenReturn(e);

		TopicProgress topicProgress = TstDataBuilder.topicProgressBuilder().topic(topic).build();
		when(homeworkDao.getHomework(eq(10))).thenReturn(homeworkBuilder().id(10).topicProgress(topicProgress).build());
	
		// Other homework expectation
		Exercise e2 = exerciseBuilder().id(27).exerciseType(ExerciseType.GENERIC).build();
		Task task2 = taskBuilder().id(47).exercise(e2).build();
		Topic topic2 = topicBuilder().id(37).task(task2).build();
		when(curriculumDao.getExercise(eq(27))).thenReturn(e);
		
		TopicProgress topicProgress2 = TstDataBuilder.topicProgressBuilder().topic(topic2).build();
		when(homeworkDao.getHomework(eq(17))).thenReturn(homeworkBuilder().id(17).topicProgress(topicProgress2).build());
		
	}

	@Test
	public void verifyThatExerciseInfoIsReturnedById() {
		// Given
		GetExerciseInfo action = new GetExerciseInfo(10, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);

		// Then
		assertThat(response.getExerciseInfo(), notNullValue());
	}

	@Test
	public void verifyThatLoadedExerciseInfoHasId() {
		// Given
		GetExerciseInfo action = new GetExerciseInfo(10, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);

		// Then
		assertThat(response.getExerciseInfo().getId(), is(20));
	}

	@Test
	public void verifyThatLoadedExerciseInfoHasType() {
		// Given
		GetExerciseInfo action = new GetExerciseInfo(10, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);

		// Then
		assertThat(response.getExerciseInfo().getExerciseType(), is(ExerciseType.GENERIC));
	}

	@Test
	public void verifyThatLoadedExerciseInfoHasHomeworkId() {
		// Given
		GetExerciseInfo action = new GetExerciseInfo(10, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);

		// Then
		assertThat(response.getExerciseInfo().getHomeworkId(), is(10));
	}

	@Test
	public void givenACaseWhenHomeworkNotFoundThenExeptionIsThrown() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.homework.notfound")));

		// Given
		GetExerciseInfo action = new GetExerciseInfo(15, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);
	}

	@Test
	public void givenACaseWhenExerciseNotFoundThenExeptionIsThrown() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.exercise.notfound")));

		// Given
		GetExerciseInfo action = new GetExerciseInfo(10, 25);

		// When
		GetExerciseInfoResponse response = whenAction(action);
	}

	@Test
	public void givenACaseWhenExerciseDoesNotBelongToHomeworkThenExeptionIsThrown() {
		// Expect
		thrown.expect(SsgGuiServiceException.class);
		thrown.expect(hasErrorCode(is("task.exercise.dosnt.belong.to.homework")));

		// Given
		GetExerciseInfo action = new GetExerciseInfo(17, 20);

		// When
		GetExerciseInfoResponse response = whenAction(action);
	}

	@Override
	protected Class<GetExerciseInfo> testedCommandClass() {
		return GetExerciseInfo.class;
	}
}
