package org.ssg.gui.server.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ssg.core.domain.ApplicationUser;

public class SpringSecurityApplicationUserProvider implements ApplicationUserProvider {

	public ApplicationUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		} else {
			return (ApplicationUser) authentication.getPrincipal();
		}
	}

}
