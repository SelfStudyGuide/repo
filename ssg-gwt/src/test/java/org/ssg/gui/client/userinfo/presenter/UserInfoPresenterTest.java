package org.ssg.gui.client.userinfo.presenter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.UserInfoHolder;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;
import org.ssg.gui.client.userinfo.event.LogoutEvent;
import org.ssg.gui.client.userinfo.event.LogoutEventHandler;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEventHandler;
import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter.Display;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoPresenterTest extends AbstractPresenterTestCase {

	@Mock
	private Display display;

	@Mock
	private HasText userNameLable;

	@Mock
	private HasClickHandlers logoutButton;

	private UserInfoPresenter presenter;

	@Before
	public void setUp() {
		presenter = new UserInfoPresenter(display, actionSender, handlerManager, windowLocation);
		when(display.getUsernameLabel()).thenReturn(userNameLable);
		when(display.getLogoutButton()).thenReturn(logoutButton);
		presenter.bind();
	}

	@Test
	public void verifyThatAfterGetUserInfoEvenIsFiredCommandIsExecuted() {
		handlerManager.fireEvent(new RetrieveUserInfoEvent());

		verifyAction(GetUserInfo.class);
	}

	@Test
	public void verifyThatAfterResponceFromServerEventIsFired() {

		final ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 1);

		AssertEventHandler eventAssert = verifyAppEvent(ShareUserInfoEvent.TYPE, new ShareUserInfoEventHandler() {
			public void onUserInfoRetrieve(ShareUserInfoEvent event) {
				assertThat(event.getUserInfo(), is(userInfo));
			}
		});

		handlerManager.fireEvent(new RetrieveUserInfoEvent());
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetUserInfo.class);

		callback.onSuccess(new GetUserInfoResponse(userInfo));

		eventAssert.assertInvoked();
	}

	@Test
	public void verifyThatAfterResponceUsernameLabelIsUpdated() {
		final ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 1);

		handlerManager.fireEvent(new RetrieveUserInfoEvent());
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetUserInfo.class);
		callback.onSuccess(new GetUserInfoResponse(userInfo));

		verify(userNameLable).setText(eq("John Dou"));
	}

	@Test
	public void verifyThatWheUserClickOnLogoutButtonBrowserWillRelocatePageToLogout() {
		ClickHandler handler = verifyAndCaptureClickHnd(logoutButton);
		handler.onClick(null);

		verify(windowLocation).replace("j_spring_security_logout");
	}

	@Test
	public void verifyThatWheUserClickOnLogoutButtonLogoutEventIsFired() {
		AssertEventHandler assertEvent = verifyAppEvent(LogoutEvent.TYPE, new LogoutEventHandler() {
			public void onLogout() {
			}
		});

		ClickHandler handler = verifyAndCaptureClickHnd(logoutButton);
		handler.onClick(null);

		assertEvent.assertInvoked();
	}

	@Test
	public void verifyThatUserInfoIsStoredInHolderAfterResponse() {
		final ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 1);

		handlerManager.fireEvent(new RetrieveUserInfoEvent());
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetUserInfo.class);
		callback.onSuccess(new GetUserInfoResponse(userInfo));

		assertThat(UserInfoHolder.getAppUsername(), equalTo("John Dou"));
		assertThat(UserInfoHolder.getStudentId(), equalTo(1));
	}

}
