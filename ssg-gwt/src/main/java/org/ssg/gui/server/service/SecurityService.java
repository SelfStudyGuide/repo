package org.ssg.gui.server.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ssg.core.domain.ApplicationUser;

public interface SecurityService {
	ApplicationUser authoriseUser(UserDetails userDetails)
			throws UsernameNotFoundException;
	
	ApplicationUser getAuthorisedUserInfo();
}
