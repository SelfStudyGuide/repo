package org.ssg.gui.client.topic.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RefreshTopicInfoEvent extends GwtEvent<RefreshTopicInfoEvent.Handler> {

	public interface Handler extends EventHandler {
		void onRefreshTopicInfo(int homeworkId, int topicId);
	}

	public static final Type<RefreshTopicInfoEvent.Handler> TYPE = new Type<RefreshTopicInfoEvent.Handler>();

	private int homeworkId;
	private int topicId;

	public RefreshTopicInfoEvent(int homeworkId, int topicId) {
		this.homeworkId = homeworkId;
		this.topicId = topicId;
	}

	@Override
	public Type<RefreshTopicInfoEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onRefreshTopicInfo(homeworkId, topicId);
	}
}
