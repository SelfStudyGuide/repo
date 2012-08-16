package org.ssg.gui.server.service;

import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.ApplicationUser;

/**
 * Security service.
 * <p>
 * This class should not contain dependency on security provider library.
 */
public interface SecurityService {

	/**
	 * Derive person id of student or teacher depends on which role the given
	 * user has.
	 * 
	 * @param user
	 *            authenticated user
	 * 
	 * @throws SsgServiceException
	 *             if student or teacher was not found.
	 */
	void enrichWithPersonId(ApplicationUser user);

}
