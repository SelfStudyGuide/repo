package org.ssg.gui.client.studenthome.event;

import com.google.gwt.event.shared.GwtEvent;

public class RefreshStudentHomeworksEvent extends
		GwtEvent<RefreshStudentHomeworksEventHandler> {

	public static final Type<RefreshStudentHomeworksEventHandler> TYPE = new Type<RefreshStudentHomeworksEventHandler>();

	public Type<RefreshStudentHomeworksEventHandler> getAssociatedType() {
		return TYPE;
	}

	protected void dispatch(RefreshStudentHomeworksEventHandler handler) {
		handler.onHomeworkRefresh();
	}
}
