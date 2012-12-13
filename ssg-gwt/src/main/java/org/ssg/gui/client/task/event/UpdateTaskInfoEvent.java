package org.ssg.gui.client.task.event;

import org.ssg.core.dto.TopicTaskDetailedInfo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UpdateTaskInfoEvent extends GwtEvent<UpdateTaskInfoEvent.Handler> {

	public static final Type<UpdateTaskInfoEvent.Handler> TYPE = new Type<UpdateTaskInfoEvent.Handler>();
	private final TopicTaskDetailedInfo taskInfo;

	public UpdateTaskInfoEvent(TopicTaskDetailedInfo taskInfo) {
		super();
		this.taskInfo = taskInfo;
	}

	public interface Handler extends EventHandler {
		void onTaskInfoUpdate(TopicTaskDetailedInfo taskInfo);
	}

	@Override
	protected void dispatch(Handler h) {
		h.onTaskInfoUpdate(taskInfo);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
