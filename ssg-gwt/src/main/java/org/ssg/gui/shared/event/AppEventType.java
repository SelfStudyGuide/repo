package org.ssg.gui.shared.event;

import com.google.web.bindery.event.shared.Event.Type;

public abstract class AppEventType<E> extends Type<AppEventHandler> {
	public abstract E getEvent(); 
}
