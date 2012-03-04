package org.ssg.gui.client.userinfo.event;

import com.google.gwt.event.shared.GwtEvent;

public class RetrieveUserInfoEvent extends
		GwtEvent<RetrieveUserInfoEventHandler> {

	public static final Type<RetrieveUserInfoEventHandler> TYPE = new Type<RetrieveUserInfoEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RetrieveUserInfoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RetrieveUserInfoEventHandler handler) {
		handler.onRetrieveUserInfo();
	}

}
