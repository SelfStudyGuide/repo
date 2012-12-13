package org.ssg.gui.client.task.presenter;

import static org.ssg.core.support.databuilder.TopicTaskInfoBuilder.topicTaskInfoBuilder;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.support.databuilder.TopicTaskDetailedInfoBuilder;
import org.ssg.core.support.databuilder.TopicTaskInfoBuilder;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.task.event.OpenExerciseEvent;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.event.UpdateTaskInfoEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerPresenterTest extends AbstractPresenterTestCase {

	private TaskControllerPresenter preseter;

	@Mock
	private TaskControllerPresenter.Display view;

	@Mock
	private HasText taskLabel;

	@Before
	public void setUp() {
		Mockito.when(view.getTaskLabel()).thenReturn(taskLabel);
		preseter = new TaskControllerPresenter(view, ssgMessages, actionSender, handlerManager);
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
		return (TopicTaskDetailedInfo) TopicTaskDetailedInfoBuilder.topicTaskDetailedInfoBuilder().id(20).type(TaskType.TEXT).exerciseIds(11, 12, 13).build();
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
		AssertEventHandler event = verifyAppEvent(OpenExerciseEvent.TYPE, OpenExerciseEvent.Handler.class);

		// given
		handlerManager.fireEvent(new OpenTaskEvent(10, 20));
		TopicTaskDetailedInfo taskInfo = topicTaskInfo();
		
		// when
		handlerManager.fireEvent(new UpdateTaskInfoEvent(taskInfo));

		// then
		Integer exerciseId = event.lastArgument(0, Integer.class);
		Assert.assertThat(exerciseId, CoreMatchers.is(11));
	}
	
	@Test
	public void verifyThatWhenOpenExerciseEventIsFiredThenGetExerciseInfoActionIsSent() {
		
	}
}
