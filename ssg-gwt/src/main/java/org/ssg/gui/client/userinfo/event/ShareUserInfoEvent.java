package org.ssg.gui.client.userinfo.event;

import org.ssg.core.dto.ApplicationUserInfo;

import com.google.gwt.event.shared.GwtEvent;

public class ShareUserInfoEvent extends GwtEvent<ShareUserInfoEventHandler> {

	private ApplicationUserInfo userInfo;

	public static Type<ShareUserInfoEventHandler> TYPE = new Type<ShareUserInfoEventHandler>();

	public ShareUserInfoEvent(ApplicationUserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ShareUserInfoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShareUserInfoEventHandler handler) {
		handler.onUserInfoRetrieve(this);
	}

	public ApplicationUserInfo getUserInfo() {
		return userInfo;
	}

}
