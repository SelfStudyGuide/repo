package org.ssg.gui.client.service;

import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.gui.client.service.res.SsgMessages;

public class TopicStatusHelper {

	private final Page page;
	private final SsgMessages messages;

	public interface Page {
		String notStarted(SsgMessages messages, TopicProgressInfo topic);

		String inProgress(SsgMessages messages, TopicProgressInfo topic);

		String done(SsgMessages messages, TopicProgressInfo topic);
	}

	public TopicStatusHelper(Page page, SsgMessages messages) {
		this.page = page;
		this.messages = messages;
	}

	public String getStatus(TopicDetailedProgressInfo topic) {
		if (topic.isNotStarted()) {
			return page.notStarted(messages, topic);
		} else if (topic.isInProgress()) {
			return page.inProgress(messages, topic);
		} else if (topic.isDone()) {
			return page.done(messages, topic);
		} else {
			return "NA";
		}
	}

	public static final Page TOPIC_DETAILS = new Page() {

		public String notStarted(SsgMessages messages, TopicProgressInfo topic) {
			return messages.topicViewTopicStatusNosStarted();
		}

		public String inProgress(SsgMessages messages, TopicProgressInfo topic) {
			return messages.topicViewTopicStatusInProgress();
		}

		public String done(SsgMessages messages, TopicProgressInfo topic) {
			return messages.topicViewTopicStatusDone();
		}
	};

}
