package org.ssg.gui.server.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.ApplicationUserImpl;

public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {

	private static final Log LOG = LogFactory.getLog(CustomJdbcUserDetailsManager.class);

	@Autowired
	private SecurityService securityService;

	@Override
	protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
	        List<GrantedAuthority> combinedAuthorities) {

		UserDetails userDetails = super.createUserDetails(username, userFromUserQuery, combinedAuthorities);

		ApplicationUserImpl applicationUser = new ApplicationUserImpl(userDetails);
		entichApplicationUserWithPersonId(applicationUser);
		
		LOG.debug("User " + applicationUser.getUsername() + " is authenticated");

		return applicationUser;
	}

	private void entichApplicationUserWithPersonId(ApplicationUserImpl applicationUser) {
	    try {
			securityService.enrichWithPersonId(applicationUser);
		} catch (SsgServiceException e) {
			throw new UsernameNotFoundException(e.getMessage(), e);
		}
    }

}
