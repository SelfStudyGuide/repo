package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;
import org.ssg.gui.server.service.ApplicationUserProvider;

public class GetUserInfoActionHandlerTest extends AbstractCommandTestCase<GetUserInfoResponse, GetUserInfo> {
	
	@Autowired(required = true)
	private ApplicationUserProvider userProvider;
	
	@Test
	public void verifyThatStudentUserInfoCanBeRetrievedFromContext() {
		when(userProvider.getUser()).thenReturn(TstDataUtils.createAppUserDetails("John", 123));

		GetUserInfoResponse response = whenAction(new GetUserInfo());
		Assert.assertThat(response, notNullValue());
		ApplicationUserInfo info = response.getUserInfo();
		Assert.assertThat(info.getStudentId(), is(123));
		Assert.assertThat(info.getUsername(), equalTo("John"));
	}

	@Test(expected = SsgGuiServiceException.class)
	public void verifyThatIfContextDoesNotContainAuthInformarionThenExceptionIsThrown() {
		when(userProvider.getUser()).thenReturn(null);

		whenAction(new GetUserInfo());
	}

	@Override
	protected Class<GetUserInfo> testedCommandClass() {
		return GetUserInfo.class;
	}

}
