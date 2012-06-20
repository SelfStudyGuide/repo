package org.ssg.gui.client.topic.presenter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.DefaultActionSender;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class TopicInfoPresenterTest extends AbstractPresenterTestCase {

	private TopicInfoPresenter presenter;

	@Mock
	private TopicInfoPresenter.Display display;

	@Mock
	private HasText topicName;

	@Mock
	private HasText topicDescription;

	@Mock
	private HasText topicProgress;

	@Before
	public void setUp() {
		DefaultActionSender actionSender = new DefaultActionSender(service,
				new DefaultActionNameProvider(), errorDialog, ssgMessages);

		when(display.getTopicName()).thenReturn(topicName);
		when(display.getTopicDescription()).thenReturn(topicDescription);
		when(display.getTopicProgress()).thenReturn(topicProgress);

		presenter = new TopicInfoPresenter(display, actionSender,
				handlerManager, ssgMessages);
		presenter.bind();
	}

	@Test
	public void verifyThatAfterRefreshTopicEventGetTopicInfoActionIsSent() {

		// When
		handlerManager.fireEvent(new RefreshTopicInfoEvent(123, 456));

		// Then
		GetTopicInfo action = verifyAction(GetTopicInfo.class);
		assertThat(action.getHomeworkId(), is(123));
		assertThat(action.getTopicId(), is(456));
	}

	@Test
	public void verifyThatTopicNameIsUpdatedWhenResponseIsReceived() {
		// Given
		handlerManager.fireEvent(new RefreshTopicInfoEvent(123, 456));
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetTopicInfo.class);

		// When
		callback.onSuccess(new GetTopicInfoResponse(TstDataUtils
				.createTopicDetailedProgressInfo(345, "Topic1")));

		// Then
		assertText(topicName, "Topic1");
	}

	@Test
	public void verifyThatTopicDescriptionIsUpdatedWhenResponseIsReceived() {
		// Given
		handlerManager.fireEvent(new RefreshTopicInfoEvent(123, 456));
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetTopicInfo.class);

		// When
		callback.onSuccess(new GetTopicInfoResponse(TstDataUtils
				.createTopicDetailedProgressInfo(345, "Topic1")));

		// Then
		assertText(topicDescription, "Some Topic description");
	}

	@Test
	public void verifyThatTopicProgressIsUpdatedWhenResponseIsReceived() {
		// Given
		handlerManager.fireEvent(new RefreshTopicInfoEvent(123, 456));
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetTopicInfo.class);

		// When
		callback.onSuccess(new GetTopicInfoResponse(TstDataUtils
				.createTopicDetailedProgressInfo(345, "Topic1")));

		// Then
		assertText(topicProgress, "In Progress");
	}

	protected void assertText(HasText hasText, String expected) {
		verify(hasText).setText(Mockito.eq(expected));
	}
}
