package org.ssg.gui.client.task.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class OpenTaskEvent extends GwtEvent<OpenTaskEvent.Handler> {

	public static final Type<OpenTaskEvent.Handler> TYPE = new Type<OpenTaskEvent.Handler>();

	private int homeworkId;
	private int taskId;
	private int exerciseId = -1;

	public OpenTaskEvent(int homeworkId, int taskId) {
		super();
		this.homeworkId = homeworkId;
		this.taskId = taskId;
	}

	public interface Handler extends EventHandler {
		void onOpenTaskEvent(OpenTaskEvent event);
	}

	public boolean isExerciseProvided() {
		return exerciseId != -1;
	}

	public int getHomeworkId() {
    	return homeworkId;
    }

	public int getTaskId() {
    	return taskId;
    }

	public int getExerciseId() {
    	return exerciseId;
    }

	@Override
	protected void dispatch(Handler h) {
		h.onOpenTaskEvent(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
