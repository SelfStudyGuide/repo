package org.ssg.gui.client.userinfo.presenter;

import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;
import org.ssg.gui.client.userinfo.event.LogoutEvent;
import org.ssg.gui.client.userinfo.event.LogoutEventHandler;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEventHandler;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEventHandler;
import org.ssg.gui.shared.WindowLocation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

public class UserInfoPresenter {

	private static final String LOGOUT_URL = "j_spring_security_logout";
	
	private StudentControlServiceAsync service;
	private HandlerManager handlerManager;
	private Display display;
	private WindowLocation windowLocation;

	public interface Display {
		HasText getUsernameLabel();
		HasClickHandlers getLogoutButton();
	}

	public UserInfoPresenter(Display display,
			StudentControlServiceAsync service, HandlerManager handlerManager,
			WindowLocation windowLocation) {
		this.display = display;
		this.service = service;
		this.handlerManager = handlerManager;
		this.windowLocation = windowLocation;
	}

	public void bind() {
		handlerManager.addHandler(RetrieveUserInfoEvent.TYPE,
				new RetrieveUserInfoEventHandler() {
					public void onRetrieveUserInfo() {
						doRetieveUserInfo();
					}
				});

		handlerManager.addHandler(ShareUserInfoEvent.TYPE,
				new ShareUserInfoEventHandler() {
					public void onUserInfoRetrieve(ShareUserInfoEvent event) {
						doUpdateUserInfo(event.getUserInfo());
					}
				});

		handlerManager.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			public void onLogout() {
				doLogout();
			}
		});

		display.getLogoutButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doFireLogoutEvent();
			}
		});
	}

	protected void doLogout() {
		windowLocation.replace(LOGOUT_URL);
	}

	protected void doFireLogoutEvent() {
		handlerManager.fireEvent(new LogoutEvent());
	}

	protected void doUpdateUserInfo(ApplicationUserInfo applicationUserInfo) {
		display.getUsernameLabel().setText(applicationUserInfo.getUsername());
	}

	protected void doRetieveUserInfo() {
//		ApplicationUserInfo userInfo = new ApplicationUserInfo();
//		userInfo.setUsername("Foo");
//		userInfo.setStudentId(1);
//		
//		handlerManager.fireEvent(new ShareUserInfoEvent(userInfo));
//		
		service.execute(new GetUserInfo(),
				new AsyncCallback<GetUserInfoResponse>() {

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(GetUserInfoResponse result) {
						GWT.log("User info " + result.getUserInfo());
						handlerManager.fireEvent(new ShareUserInfoEvent(result
								.getUserInfo()));
					}
				});
	}

}
