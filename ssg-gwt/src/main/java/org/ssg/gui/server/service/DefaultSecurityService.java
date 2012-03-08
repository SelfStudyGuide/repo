package org.ssg.gui.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.service.StudentService;
import org.ssg.gui.server.ApplicationMessageSource;

@Service
public class DefaultSecurityService implements SecurityService {

	public static final String ROLE_STUDENT = "student";
	public static final String ROLE_TEACHER = "teacher";
	
	@Autowired
	private StudentService studentService;
	
	private MessageSourceAccessor applicationMsg = ApplicationMessageSource.getAccessor();
	
	public ApplicationUser authoriseUser(UserDetails userDetails) {

		int personeId = 0;
		if (userHasRole(userDetails, ROLE_STUDENT)) {
			personeId = retriveStudentIdByName(userDetails);
		} else if (userHasRole(userDetails, ROLE_TEACHER)) {
			// TODO: derive teacher id;
		} else {
			throw new UsernameNotFoundException(applicationMsg.getMessage(
					"ssg.auth.userDoesNotHaveRole",
					new String[] { userDetails.getUsername() }));
		}

		return new ApplicationUser(userDetails, personeId);
	}
	
	private int retriveStudentIdByName(UserDetails userDetails) {
		try {
			return studentService.getStudentIdByName(userDetails.getUsername());
		} catch (SsgServiceException exception) {
			throw new UsernameNotFoundException(exception.getMessage(), exception);
		}
	}

	private boolean userHasRole(UserDetails userDetails, String role) {
		for (GrantedAuthority a : userDetails.getAuthorities()) {
			if (a.getAuthority().equals(role)) {
				return true;
			}
		}
		
		return false;
	}

	public ApplicationUser getAuthorisedUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null) {
			return null;
		} else {
			return (ApplicationUser) authentication.getPrincipal();
		}
	}
	
	

}
