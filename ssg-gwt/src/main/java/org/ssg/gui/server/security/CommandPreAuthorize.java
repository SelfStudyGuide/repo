package org.ssg.gui.server.security;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.service.SsgGuiServiceException;

/**
 * 
 */
public interface CommandPreAuthorize<A extends Action<?>> {
	void preAuthorize(A action) throws SsgSecurityException, SsgGuiServiceException;
}
