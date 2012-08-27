package org.ssg.gui.server.security;

import org.ssg.core.common.SsgServiceException;

/**
 * Security exception. In case if user is not authorized to get access to a
 * object.
 */
public class SsgSecurityException extends SsgServiceException {

	private static final long serialVersionUID = 3228901115272787282L;

	public SsgSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SsgSecurityException(String message) {
		super(message);
	}

}
