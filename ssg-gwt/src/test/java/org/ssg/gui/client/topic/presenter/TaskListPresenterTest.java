package org.ssg.gui.client.topic.presenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.ssg.core.support.databuilder.TopicTaskInfoBuilder.topicTaskInfoBuilder;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.core.support.databuilder.TopicTaskInfoBuilder;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class TaskListPresenterTest extends AbstractPresenterTestCase {

	private TaskListPresenter presenter;

	@Mock
	private TaskListPresenter.Display display;

	@Mock
	private HasText textTask;

	@Mock
	private HasText listeningTask;

	@Mock
	private HasText lexicalTask;

	@Before
	public void setUp() {

		presenter = new TaskListPresenter(display, handlerManager, actionSender, windowLocation, ssgMessages);
		presenter.bind();

		when(display.getTask(Mockito.eq(10), Mockito.anyString())).thenReturn(textTask);
		when(display.getTask(Mockito.eq(20), Mockito.anyString())).thenReturn(listeningTask);
		when(display.getTask(Mockito.eq(30), Mockito.anyString())).thenReturn(lexicalTask);

	}

	@Test
	public void givenRefreshTopicInfoEventFiredThenActionGetTopicTaskInfoSent() {
		handlerManager.fireEvent(new RefreshTopicInfoEvent(1, 2));

		GetTopicTasksInfo action = verifyAction(GetTopicTasksInfo.class);
		assertThat(action.getHomeworkId(), is(1));
		assertThat(action.getTopicId(), is(2));
	}

	@Test
	public void givenGetTopicTaskInfoResponseThenThreeButtonsIsAdded() {
		processGetTopicTaksInfoResponse();

		// Then
		verify(display, times(3)).getTask(Mockito.anyInt(), Mockito.anyString());
	}

	private void processGetTopicTaksInfoResponse() {
		// Given
		handlerManager.fireEvent(new RefreshTopicInfoEvent(1, 2));
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetTopicTasksInfo.class);

		// When
		callback.onSuccess(new GetTopicTasksInfoResponse(taskInfos()));
	}

	@Test
	public void givenGetTopicTaskInfoResponseThenTEXTButtonShouldBePopulated() {
		processGetTopicTaksInfoResponse();

		// Then
		verify(textTask).setText("Text [1/10]");
	}

	@Test
	public void givenGetTopicTaskInfoResponseThenLISTENINGButtonShouldBePopulated() {
		processGetTopicTaksInfoResponse();

		// Then
		verify(listeningTask).setText("Listening [0/5]");
	}

	@Test
	public void givenGetTopicTaskInfoResponseThenLEXICALButtonShouldBePopulated() {
		processGetTopicTaksInfoResponse();

		// Then
		verify(lexicalTask).setText("Lexical [10/10]");
	}

	@Test
	public void givenTEXTtaskInfoWhenProcessingResponseThenHrefShouldBePassed() {
		processGetTopicTaksInfoResponse();

		// Then
		ArgumentCaptor<String> href = ArgumentCaptor.forClass(String.class);
		verify(display).getTask(Matchers.eq(10), href.capture());
		href.getAllValues();
		assertThat(href.getValue(), is("http://localhost/ssg/Task.jsp?hid=1&taid=10"));
	}

	@Test
	public void givenLISTENINGtaskInfoWhenProcessingResponseThenHrefShouldBePassed() {
		processGetTopicTaksInfoResponse();

		// Then
		ArgumentCaptor<String> href = ArgumentCaptor.forClass(String.class);
		verify(display).getTask(Matchers.eq(20), href.capture());
		href.getAllValues();
		assertThat(href.getValue(), is("http://localhost/ssg/Task.jsp?hid=1&taid=20"));
	}

	@Test
	public void givenLEXICALtaskInfoWhenProcessingResponseThenHrefShouldBePassed() {
		processGetTopicTaksInfoResponse();

		// Then
		ArgumentCaptor<String> href = ArgumentCaptor.forClass(String.class);
		verify(display).getTask(Matchers.eq(30), href.capture());
		href.getAllValues();
		assertThat(href.getValue(), is("http://localhost/ssg/Task.jsp?hid=1&taid=30"));
	}

	private List<TopicTaskInfo> taskInfos() {
		return Arrays.asList(topicTaskInfoBuilder().id(10).type(TaskType.TEXT).execrisesCount(10).completedCount(1)
		        .build(), topicTaskInfoBuilder().id(20).type(TaskType.LISTENING).execrisesCount(5).completedCount(0)
		        .build(), topicTaskInfoBuilder().id(30).type(TaskType.LEXICAL).execrisesCount(10).completedCount(10)
		        .build());
	}

}
