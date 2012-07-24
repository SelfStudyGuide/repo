package org.ssg.gui.security;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.service.StudentService;
import org.ssg.gui.server.service.CustomJdbcUserDetailsManager;

@ContextConfiguration(locations = { "/spring/app-securty.ctx.xml", "/spring/gui-service.ctx.xml",
		"/serice-core-mock.ctx.xml", "/spring/db-config.xml",
		"/test-config.ctx.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SecurtyIntegrationTest  {

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private ProviderManager authenticationManager;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CustomJdbcUserDetailsManager userDetailsManager;


	@Before
	public void setUp() {
	}

	private void createUser(String userName, String role) {
		userDetailsManager.deleteUser(userName);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));
		userDetailsManager.createUser(new User(userName, "pass", authorities));
		// return userDetailsManager.loadUserByUsername(userName);
	}

	@Test
	public void verifyThatStudentCanBeAuthenticatedWithCredentials() {
		createUser("user", "student");

		when(studentService.getStudentIdByName("user")).thenReturn(123);

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"user", "pass");
		Authentication a = authenticationManager.authenticate(authentication);

		assertThat(a.getAuthorities().size(), is(1));
		assertThat(a.getAuthorities().iterator().next().getAuthority(),
				equalTo("student"));
		assertThat((ApplicationUser)a.getPrincipal(), is(ApplicationUser.class));
		assertThat(((ApplicationUser) a.getPrincipal()).getUsername(),
				equalTo("user"));
		assertThat(((ApplicationUser) a.getPrincipal()).getPersonId(), is(123));

		verify(studentService).getStudentIdByName(eq("user"));

	}
	
	@Test
	public void verifyThatTeacherCanBeAuthenticatedWithCredentials() {
		createUser("user2", "teacher");
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"user2", "pass");
		Authentication a = authenticationManager.authenticate(authentication);

		assertThat(a.getAuthorities().size(), is(1));
		assertThat(a.getAuthorities().iterator().next().getAuthority(),
				equalTo("teacher"));
		assertThat((ApplicationUser)a.getPrincipal(), is(ApplicationUser.class));
		assertThat(((ApplicationUser) a.getPrincipal()).getUsername(),
				equalTo("user2"));
		//assertThat(((ApplicationUser) a.getPrincipal()).getPersonId(), is(123));
	}
	
	//@Test(expected = UsernameNotFoundException.class)
	@Test(expected = BadCredentialsException.class)
	public void verifyThatExceptionIsThrownIfStudentNotFound() {
		createUser("user3", "student");
		
		when(studentService.getStudentIdByName("user3")).thenThrow(new SsgServiceException(""));
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"user3", "pass");
		authenticationManager.authenticate(authentication);
	}
	
	@Test(expected = BadCredentialsException.class)
	public void verifyThatExceptionIsThrownWhenUserDoesNotHaveRoles() {
		createUser("user4", "other_role");
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				"user4", "pass");
		authenticationManager.authenticate(authentication);
	}

}
