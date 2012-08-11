package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;

@ContextConfiguration(locations = { "/spring/gui-service.ctx.xml", "/serice-core-mock.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GetUserInfoActionHandlerTest extends AbstractCommandTestCase<GetUserInfoResponse, GetUserInfo> {

	@Mock
	private SecurityContext context;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		SecurityContextHolder.setContext(context);
	}

	@Test
	public void verifyThatStudentUserInfoCanBeRetrievedFromContext() {
		ApplicationUser user = TstDataUtils.createAppUserDetails("John", 123);
		when(context.getAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken(user, ""));

		GetUserInfoResponse response = whenAction(new GetUserInfo());
		Assert.assertThat(response, notNullValue());
		ApplicationUserInfo info = response.getUserInfo();
		Assert.assertThat(info.getStudentId(), is(123));
		Assert.assertThat(info.getUsername(), equalTo("John"));
	}

	@Test(expected = SsgGuiServiceException.class)
	public void verifyThatIfContextDoesNotContainAuthInformarionThenExceptionIsThrown() {
		when(context.getAuthentication()).thenReturn(null);

		whenAction(new GetUserInfo());
	}

	@Override
	protected Class<GetUserInfo> testedCommandClass() {
		return GetUserInfo.class;
	}

}
