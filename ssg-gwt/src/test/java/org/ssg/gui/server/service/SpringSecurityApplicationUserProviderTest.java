package org.ssg.gui.server.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.ssg.core.support.TstDataUtils.createAppUserDetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.ApplicationUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/app-securty.ctx.xml", "/gui-service-mock.ctx.xml",
        "/serice-core-mock.ctx.xml" })
public class SpringSecurityApplicationUserProviderTest {

	@Mock
	private SecurityContext context;

	@Autowired(required = true)
	private ApplicationUserProvider provider;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		SecurityContextHolder.setContext(context);
	}

	@Test
	public void givenSecurityContextWhenGettingUserThenApplicationUserShouldBeReturned() {
		// Given
		when(context.getAuthentication()).thenReturn(authenticationToken());

		// When
		ApplicationUser user = provider.getUser();

		// Then
		assertThat(user.getUsername(), is("John"));
		assertThat(user.getPersonId(), is(10));
	}

	@Test
	public void givenEmptySecurityContextWhenGettingUserThenNullShouldBeReturned() {
		// Given
		when(context.getAuthentication()).thenReturn(null);

		// When
		ApplicationUser user = provider.getUser();

		// Then
		assertThat(user, nullValue());
	}

	private UsernamePasswordAuthenticationToken authenticationToken() {
		return new UsernamePasswordAuthenticationToken(createAppUserDetails("John", 10), "");
	}
}
