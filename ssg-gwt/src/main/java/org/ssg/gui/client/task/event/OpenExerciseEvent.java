package org.ssg.gui.client.task.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class OpenExerciseEvent extends GwtEvent<OpenExerciseEvent.Handler> {

	public static final Type<OpenExerciseEvent.Handler> TYPE = new Type<OpenExerciseEvent.Handler>();

	private int exerciseId;
	private int homeworkId;

	public OpenExerciseEvent(int homeworkId, int exerciseId) {
		super();
		this.homeworkId = homeworkId;
		this.exerciseId = exerciseId;
	}

	public interface Handler extends EventHandler {
		void onOpenExerciseEvent(OpenExerciseEvent event);
	}

	public int getExerciseId() {
    	return exerciseId;
    }
	
	public int getHomeworkId() {
    	return homeworkId;
    }

	@Override
	protected void dispatch(Handler h) {
		h.onOpenExerciseEvent(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
