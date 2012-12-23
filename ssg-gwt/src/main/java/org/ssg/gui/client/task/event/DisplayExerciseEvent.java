package org.ssg.gui.client.task.event;

import org.ssg.core.dto.ExerciseInfo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DisplayExerciseEvent extends GwtEvent<DisplayExerciseEvent.Handler> {

	public static final Type<DisplayExerciseEvent.Handler> TYPE = new Type<DisplayExerciseEvent.Handler>();

	private final ExerciseInfo exerciseInfo;

	public DisplayExerciseEvent(ExerciseInfo exerciseInfo){
	    this.exerciseInfo = exerciseInfo;
    }

	public interface Handler extends EventHandler {
		void onDisplayExerciseEvent(DisplayExerciseEvent event);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.onDisplayExerciseEvent(this);
	}

	public ExerciseInfo getExerciseInfo() {
		return exerciseInfo;
	}

}
