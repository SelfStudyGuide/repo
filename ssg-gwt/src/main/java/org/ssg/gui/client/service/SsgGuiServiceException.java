package org.ssg.gui.client.service;

/**
 * Class of base exception for GWT client.
 */
public class SsgGuiServiceException extends RuntimeException {

	private static final long serialVersionUID = 394554731771570163L;

	private String errorCode;

	/**
	 * For GWT marshaler.
	 */
	public SsgGuiServiceException() {
		super();
	}

	public SsgGuiServiceException(String message) {
		super(message);
	}

	public SsgGuiServiceException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
