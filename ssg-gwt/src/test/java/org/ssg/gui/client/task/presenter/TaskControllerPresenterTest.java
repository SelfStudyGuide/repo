package org.ssg.gui.client.task.presenter;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.ExerciseInfo;
import org.ssg.core.dto.ExerciseType;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.core.support.databuilder.ExerciseInfoBuilder;
import org.ssg.core.support.databuilder.TopicTaskDetailedInfoBuilder;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.studenthome.event.HomeworkSelectedEvent;
import org.ssg.gui.client.task.action.GetExerciseInfo;
import org.ssg.gui.client.task.action.GetExerciseInfoResponse;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.task.event.DisplayExerciseEvent;
import org.ssg.gui.client.task.event.OpenExerciseEvent;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.event.UpdateTaskInfoEvent;
import org.ssg.gui.client.task.view.ExerciseView;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;

// TaskControllerPresenter
@RunWith(MockitoJUnitRunner.class)
public class TaskControllerPresenterTest extends AbstractPresenterTestCase {

	private TaskControllerPresenter preseter;

	@Mock
	private TaskControllerPresenter.Display view;

	@Mock
	private HasText taskLabel;
	
	@Mock
	private ForIsWidget exercisePanel;
	
	@Mock 
	private ExerciseView exerciseView;

	@Mock
	private ExerciseViewProvider exerciseViewProvider;

	@Before
	public void setUp() {
		when(view.getTaskLabel()).thenReturn(taskLabel);
		when(view.getExercisePanel()).thenReturn(exercisePanel);
		
		when(exerciseViewProvider.getExerciseView(same(ExerciseType.GENERIC))).thenReturn(exerciseView);
		
		preseter = new TaskControllerPresenter(view, ssgMessages, actionSender, handlerManager, exerciseViewProvider);
		preseter.bind();
	}

	@Test
	public void verifyThatGetTaskInfoActionIsSentToServerAfterOpenTaskEvent() {

		// When
		handlerManager.fireEvent(new OpenTaskEvent(10, 20));

		// Then
		GetTaskInfo action = verifyAction(GetTaskInfo.class);
		Assert.assertThat(action.getHomeworkId(), CoreMatchers.is(10));
		Assert.assertThat(action.getTaskId(), CoreMatchers.is(20));
	}

	@Test
	public void verifyThatTaskLabelIsPopulatedWhenTaskInfoIsReceived() {
		// Given
		TopicTaskDetailedInfo topicTaskInfo = topicTaskInfo();

		// When
		handlerManager.fireEvent(new OpenTaskEvent(10, 20));
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetTaskInfo.class);
		callback.onSuccess(new GetTaskInfoResponse(topicTaskInfo));

		// Then
		Mockito.verify(taskLabel).setText(Matchers.eq("TEXT Task"));
	}

	private TopicTaskDetailedInfo topicTaskInfo() {
		return TopicTaskDetailedInfoBuilder.topicTaskDetailedInfoBuilder().id(20)
		        .type(TaskType.TEXT).exerciseIds(11, 12, 13).build();
	}

	@Test
	public void verifyThatWhenNoExerciseIsOpenedThenOpenExerciseEventIsFiredAfterTaskInfoUpdate() {
		// expect
		AssertEventHandler event = verifyAppEvent(OpenExerciseEvent.TYPE, OpenExerciseEvent.Handler.class);

		// given
		TopicTaskDetailedInfo taskInfo = topicTaskInfo();

		// when
		handlerManager.fireEvent(new OpenTaskEvent(10, 20));
		handlerManager.fireEvent(new UpdateTaskInfoEvent(taskInfo));

		// then
		event.assertInvoked();
	}

	@Test
	public void verifyThatIfNoExerciseIdProvidedThenFirstIsOpened() {
		// expect
		AssertEventHandler eventHandler = verifyAppEvent(OpenExerciseEvent.TYPE, OpenExerciseEvent.Handler.class);

		// given
		handlerManager.fireEvent(new OpenTaskEvent(10, 20));
		TopicTaskDetailedInfo taskInfo = topicTaskInfo();

		// when
		handlerManager.fireEvent(new UpdateTaskInfoEvent(taskInfo));

		// then
		OpenExerciseEvent event = eventHandler.lastArgument(0, OpenExerciseEvent.class);
		assertThat(event.getExerciseId(), CoreMatchers.is(11));
	}

	@Test
	public void verifyThatWhenOpenExerciseEventIsFiredThenGetExerciseInfoActionIsSent() {
		// Given

		// When
		handlerManager.fireEvent(new OpenExerciseEvent(10, 11));

		// Then
		GetExerciseInfo anAction = verifyAction(GetExerciseInfo.class);
		assertThat(anAction.getExerciseId(), CoreMatchers.is(11));
		assertThat(anAction.getHomeworkId(), CoreMatchers.is(10));

	}

	@Test
	public void givenGenericExerciseReceivedFromServerThenCorrespondedPresenterIsCreated() {
		// Given
		handlerManager.fireEvent(new OpenExerciseEvent(10, 11));
		
		// When
		AsyncCallback<Response> asyncCallback = verifyActionAndResturnCallback(GetExerciseInfo.class);
		ExerciseInfo exerciseInfo = ExerciseInfoBuilder.exerciseInfoBuilder().id(11).exerciseType(ExerciseType.GENERIC).homeworkId(10).build();
		asyncCallback.onSuccess(new GetExerciseInfoResponse(exerciseInfo));
		
		// Then
		verify(exerciseViewProvider).getExerciseView(same(ExerciseType.GENERIC));
		verify(exerciseView).go(same(exercisePanel));
		
	}
	
	@Test
	public void givenGenericExerciseReceivedFromServerThenDisplayExerciseEventIsFired() {
		// Expect
		AssertEventHandler event = verifyAppEvent(DisplayExerciseEvent.TYPE,
				DisplayExerciseEvent.Handler.class);

		// Given
		handlerManager.fireEvent(new OpenExerciseEvent(10, 11));
		
		// When
		AsyncCallback<Response> asyncCallback = verifyActionAndResturnCallback(GetExerciseInfo.class);
		ExerciseInfo exerciseInfo = ExerciseInfoBuilder.exerciseInfoBuilder().id(11).exerciseType(ExerciseType.GENERIC).homeworkId(10).build();
		asyncCallback.onSuccess(new GetExerciseInfoResponse(exerciseInfo));
		
		// Then
		event.assertInvoked();
	}
}
