package org.ssg.gui.client.userinfo;

import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEventHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;

/**
 * This class listening {@link ShareUserInfoEvent} and fire an application event
 * when user info is received. To specify instance of the application event use
 * {@link #setEvent(GwtEvent)} method.
 */
public class FireEventWhenUserInfo implements ShareUserInfoEventHandler {

	private HandlerManager handlerManager;
	private GwtEvent<?> event;

	public FireEventWhenUserInfo(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
		handlerManager.addHandler(ShareUserInfoEvent.TYPE, this);
	}

	public void onUserInfoRetrieve(ShareUserInfoEvent userInfoEvent) {
		if (event != null) {
			handlerManager.fireEvent(event);
		}
	}

	public void setEvent(GwtEvent<?> event) {
		this.event = event;
	}

}
