package org.ssg.gui.client.service;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.support.TstDataUtils;

@RunWith(MockitoJUnitRunner.class)
public class TopicStatusHelperTest {
	private TopicStatusHelper helper;

	@Mock
	private SsgMessages messages;

	@Before
	public void setUp() {
		helper = new TopicStatusHelper(TopicStatusHelper.TOPIC_DETAILS,
				messages);

		TstDataUtils.createMockExpectationFor(messages);
	}

	@Test
	public void verifyThatIfTopicIsNotStartedAtTopicPageThenMessageIs_Not_Started() {
		// Given
		TopicDetailedProgressInfo topic = TstDataUtils
				.createTopicDetailedProgressInfo(1, "name");
		topic.setStatus("0");

		// When
		String status = helper.getStatus(topic);

		// Then
		assertThat(status, CoreMatchers.is("Not Started"));
		verify(messages).topicViewTopicStatusNosStarted();
	}

	@Test
	public void verifyThatIfTopicIsInProgressAtTopicPageThenMessageIs_In_Progress() {
		// Given
		TopicDetailedProgressInfo topic = TstDataUtils
				.createTopicDetailedProgressInfo(1, "name");
		topic.setStatus("20");

		// When
		String status = helper.getStatus(topic);

		// Then
		assertThat(status, CoreMatchers.is("In Progress"));
		verify(messages).topicViewTopicStatusInProgress();
	}

	@Test
	public void verifyThatIfTopicIsDoneAtTopicPageThenMessageIs_Done() {
		// Given
		TopicDetailedProgressInfo topic = TstDataUtils
				.createTopicDetailedProgressInfo(1, "name");
		topic.setStatus("100");

		// When
		String status = helper.getStatus(topic);

		// Then
		assertThat(status, CoreMatchers.is("Done"));
		verify(messages).topicViewTopicStatusDone();
	}

	@Test
	public void verifyThatIfTopicHasIncorresctPersentageAtTopicPageThenMessageIs_NA() {
		// Given
		TopicDetailedProgressInfo topic = TstDataUtils
				.createTopicDetailedProgressInfo(1, "name");
		topic.setStatus("120");

		// When
		String status = helper.getStatus(topic);

		// Then
		Assert.assertThat(status, CoreMatchers.is("NA"));
	}

}
