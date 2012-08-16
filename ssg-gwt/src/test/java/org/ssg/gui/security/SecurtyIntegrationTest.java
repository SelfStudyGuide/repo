package org.ssg.gui.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.ApplicationUserImpl;
import org.ssg.core.service.UserDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.server.service.CustomJdbcUserDetailsManager;

@ContextConfiguration(locations = { "/spring/app-securty.ctx.xml", "/spring/gui-service.ctx.xml",
        "/serice-core-mock.ctx.xml", "/spring/db-config.xml", "/test-config.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurtyIntegrationTest {

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private ProviderManager authenticationManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomJdbcUserDetailsManager userDetailsManager;

	@Before
	public void setUp() {
	}

	private void createUser(String userName, String role) {
		userDetailsManager.deleteUser(userName);
		userDetailsManager.createUser(new User(userName, "pass", Arrays.asList(new SimpleGrantedAuthority(role))));
	}

	@Test
	public void verifyThatStudentCanBeAuthenticatedWithCredentials() {
		// Given
		createUser("user", "student");
		when(userDao.getStudentByName(Mockito.eq("user"))).thenReturn(TstDataUtils.createStudent(123, "user"));
		Authentication authentication = new UsernamePasswordAuthenticationToken("user", "pass");

		// When
		Authentication a = authenticationManager.authenticate(authentication);

		// Then
		assertThat(a.getAuthorities().size(), is(1));
		assertThat(a.getAuthorities().iterator().next().getAuthority(), equalTo("student"));
		assertThat((ApplicationUserImpl) a.getPrincipal(), is(ApplicationUserImpl.class));
		assertThat(((ApplicationUserImpl) a.getPrincipal()).getUsername(), equalTo("user"));
		assertThat(((ApplicationUser) a.getPrincipal()).getPersonId(), is(123));

		verify(userDao).getStudentByName(Mockito.eq("user"));
	}

	@Test
	public void verifyThatTeacherCanBeAuthenticatedWithCredentials() {
		// Given
		createUser("user2", "teacher");
		Authentication authentication = new UsernamePasswordAuthenticationToken("user2", "pass");

		// When
		Authentication a = authenticationManager.authenticate(authentication);

		// Then
		assertThat(a.getAuthorities().size(), is(1));
		assertThat(a.getAuthorities().iterator().next().getAuthority(), equalTo("teacher"));
		assertThat((ApplicationUserImpl) a.getPrincipal(), is(ApplicationUserImpl.class));
		assertThat(((ApplicationUserImpl) a.getPrincipal()).getUsername(), equalTo("user2"));
	}

	@Test(expected = BadCredentialsException.class)
	public void verifyThatExceptionIsThrownIfStudentNotFound() {
		// Given
		createUser("user3", "student");
		when(userDao.getStudentByName(Mockito.eq("user"))).thenReturn(null);
		Authentication authentication = new UsernamePasswordAuthenticationToken("user3", "pass");

		// When
		authenticationManager.authenticate(authentication);
	}

	@Test(expected = BadCredentialsException.class)
	public void verifyThatExceptionIsThrownWhenUserDoesNotHaveRoles() {
		createUser("user4", "other_role");
		Authentication authentication = new UsernamePasswordAuthenticationToken("user4", "pass");

		authenticationManager.authenticate(authentication);
	}

}
