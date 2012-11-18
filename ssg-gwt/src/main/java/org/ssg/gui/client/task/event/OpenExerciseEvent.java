package org.ssg.gui.client.task.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class OpenExerciseEvent extends GwtEvent<OpenExerciseEvent.Handler> {

	public static final Type<OpenExerciseEvent.Handler> TYPE = new Type<OpenExerciseEvent.Handler>();

	private int exerciseId;

	public OpenExerciseEvent(int exerciseId) {
		super();
		this.exerciseId = exerciseId;
	}

	public interface Handler extends EventHandler {
		void onOpenExerciseEvent(int execriseId);
	}

	public int getExerciseId() {
    	return exerciseId;
    }

	@Override
	protected void dispatch(Handler h) {
		h.onOpenExerciseEvent(exerciseId);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
