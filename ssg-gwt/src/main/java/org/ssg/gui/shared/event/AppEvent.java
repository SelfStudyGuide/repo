package org.ssg.gui.shared.event;

import com.google.web.bindery.event.shared.Event;

public class AppEvent extends Event<AppEventHandler> {

	private final AppEventType<?> type;

	public AppEvent(AppEventType<?> type) {
		this.type = type;
	}
	
	@Override
	public com.google.web.bindery.event.shared.Event.Type<AppEventHandler> getAssociatedType() {
		return type;
	}

	@Override
	protected void dispatch(AppEventHandler handler) {
		handler.handleEvent(this);		
	}

}
