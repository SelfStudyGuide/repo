package org.ssg.gui.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.ApplicationUserImpl;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Student;
import org.ssg.core.service.UserDao;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.SsgSecurityException;
import org.ssg.gui.server.service.CustomJdbcUserDetailsManager;

@ActiveProfiles({"junit", "junit-mock-core", "real-security", "real-gui"})
@ContextConfiguration(locations = { "classpath:/spring/test-application.ctx.xml", "/spring/db-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringSecurityIntegrationTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	@Qualifier("org.springframework.security.authenticationManager")
	private ProviderManager authenticationManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private CustomJdbcUserDetailsManager userDetailsManager;

	@Autowired(required = true)
	private Authorization authorization;

//	@Autowired
//	private HomeworkDao homeworkDao;

	private Student student;

	@Before
	public void setUp() {
		reset(userDao);
	}

	private void createUser(String userName, String role) {
		userDetailsManager.deleteUser(userName);
		userDetailsManager.createUser(new User(userName, "pass", Arrays.asList(new SimpleGrantedAuthority(role))));
	}

//	@Test
//	public void verifyThatPreAuthoriseAnnoationIsHandled() {
//		// Given
//		authenticatedUser(createStudent("user", "student").getName());
//
//		// When
//		authorization.ownStudent(126);
//	}

	@Test
	public void givenUserWithStudentRoleThenAuthorisationForStudentShouldPass() {
		// Given
		authenticatedUser(createStudent("user", "student").getName());

		// When
		authorization.student();

	}

	@Test
	public void givenUserWithNoStudentRoleThenAuthorisationForStudentShouldFail() {
		// Given
		authenticatedUser(createStudent("user", "teacher").getName());

		// Then
		thrown.expect(SsgSecurityException.class);
		thrown.expectMessage("Only student can have access");

		// When
		authorization.student();
	}

	@Test
	public void givenStundetsHomeworkThenAuthorisationForOwnHomeworkShouldPass() {
		// Given
		authenticatedUser(createStudent("user", "student").getName());
		Homework hw = TstDataUtils.createHomework(student, null);

		// When
		authorization.ownHomework(hw);
	}
	
	@Test
	public void givenOtherStudentsHomeworkThenAuthorisationForOwnHomeworkShouldFail() {
		// Given
		authenticatedUser(createStudent("user", "student").getName());
		Student otherStudent = TstDataUtils.createStudent(456, "Bob");
		Homework otherHw = TstDataUtils.createHomework(otherStudent, null);

		// Then
		thrown.expect(SsgSecurityException.class);
		thrown.expectMessage("You do not have access to this homework");
		
		// When
		authorization.ownHomework(otherHw);
	}

	private void authenticatedUser(String userName) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(userName, "pass");
		Authentication a = authenticationManager.authenticate(authentication);
		SecurityContextHolder.getContext().setAuthentication(a);
	}

	private Student createStudent(String userName, String userRole) {
	    createUser(userName, userRole);
		student = TstDataUtils.createStudent(123, userName);
		when(userDao.getStudentByName(Mockito.eq(userName))).thenReturn(student);
		return student;
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
