package org.ssg.gui.client.studenthome.event;

import org.ssg.core.dto.HomeworkInfo;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class HomeworkSelectedEvent extends GwtEvent<HomeworkSelectedEvent.Handler> {

	public interface Handler extends EventHandler {
		void onHomeworkSelection(HomeworkInfo selected);

		void onHomeworkDeselected();
	}

	private HomeworkInfo selected;

	/**
	 * 
	 * @param selected
	 *            is <tt>null</tt> then unselected event
	 */
	public HomeworkSelectedEvent(HomeworkInfo selected) {
		this.selected = selected;
	}

	public static final Type<HomeworkSelectedEvent.Handler> TYPE = new Type<HomeworkSelectedEvent.Handler>();

	public Type<HomeworkSelectedEvent.Handler> getAssociatedType() {
		return TYPE;
	}

	protected void dispatch(HomeworkSelectedEvent.Handler handler) {
		if (selected()) {
			handler.onHomeworkSelection(selected);
		} else {
			handler.onHomeworkDeselected();
		}
	}

	private boolean selected() {
		return selected != null;
	}

}
