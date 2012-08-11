package org.ssg.core.common;

public class SsgServiceException extends RuntimeException {

	private static final long serialVersionUID = -2444103115631961492L;

	public SsgServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public SsgServiceException(String message) {
		super(message);
	}

}
