package org.ssg.gui.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.ssg.core.domain.ApplicationUser;

public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

	@Autowired
	private SecurityService securityService;

	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
	        List<GrantedAuthority> combinedAuthorities) {

		UserDetails userDetails = super.createUserDetails(username, userFromUserQuery, combinedAuthorities);

		ApplicationUser applicationUser = securityService.authoriseUser(userDetails);

		System.out.println("Autorised user " + applicationUser.getUsername());

		return applicationUser;
	}

}
