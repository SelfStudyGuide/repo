package org.ssg.gui.server.service;

import org.ssg.core.domain.ApplicationUser;

/**
 * Provides instance of authenticated {@link ApplicationUser} depends on
 * Security Provider.
 */
public interface ApplicationUserProvider {
	/**
	 * @return instance of {@link ApplicationUser} if user is authenticated.
	 *         Return <code>null</code> if not.
	 */
	ApplicationUser getUser();
}
