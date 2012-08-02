package org.ssg.gui.client.topic.presenter;

import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.TopicStatusHelper;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.ActionSender;
import org.ssg.gui.client.topic.action.GetTopicInfo;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

public class TopicInfoPresenter implements RefreshTopicInfoEvent.Handler {

	private ActionSender actionSender;
	private HandlerManager handlerManager;
	private Display display;
	private SsgMessages messages;
	private TopicStatusHelper topicStatusHelper;

	public interface Display {
		HasText getTopicName();

		HasText getTopicDescription();

		HasText getTopicProgress();
	}

	public TopicInfoPresenter(Display display,
			ActionSender actionSender, HandlerManager handlerManager,
			SsgMessages messages) {
		this.display = display;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
		this.messages = messages;
		this.topicStatusHelper = new TopicStatusHelper(
				TopicStatusHelper.TOPIC_DETAILS, messages);
	}

	public void bind() {
		handlerManager.addHandler(RefreshTopicInfoEvent.TYPE, this);
	}

	public void onRefreshTopicInfo(int homeworkId, int topicId) {
		actionSender.send(new GetTopicInfo(homeworkId, topicId),
				handleTopicInfo());
	}

	private ActionResponseCallback<GetTopicInfoResponse> handleTopicInfo() {
		return new ActionCallbackAdapter<GetTopicInfoResponse>() {

			public void onResponse(GetTopicInfoResponse response) {
				display.getTopicName().setText(
						response.getInfo().getTopicName());
				display.getTopicDescription().setText(
						response.getInfo().getDescription());
				display.getTopicProgress().setText(
						topicStatusHelper.getStatus((response.getInfo())));
			}
		};
	}
}
