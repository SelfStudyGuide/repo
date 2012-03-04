package org.ssg.gui.client.userinfo.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEventHandler> {

	public static final Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LogoutEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LogoutEventHandler handler) {
		handler.onLogout();
	}

}
