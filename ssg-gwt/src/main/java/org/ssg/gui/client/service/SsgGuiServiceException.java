package org.ssg.gui.client.service;

import org.ssg.gui.client.service.res.SsgLookupMessages;

/**
 * Class of base exception for GWT client.
 */
public class SsgGuiServiceException extends RuntimeException {

	private static final long serialVersionUID = 394554731771570163L;

	private String errorCode;
	
	private transient boolean handled;

	/**
	 * For GWT marshaler.
	 */
	public SsgGuiServiceException() {
		super();
	}

	public SsgGuiServiceException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message description of error
	 * @param errorCode message key which can be registered in {@link SsgLookupMessages}}
	 */
	public SsgGuiServiceException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 *  Returns message key which can be registered in {@link SsgLookupMessages}
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Indicates whether this exception was handled by presented classes.
	 * <p>
	 * If not then application tries to lookup message by {@link #getErrorCode()} 
	 * and display error dialog.
	 */
	public boolean isHandled() {
		return handled;
	}

	/**
	 * Specifies if this exception has been handled and dont needed default processing.
	 * 
	 * @see #isHandled()
	 */
	public void setHandled(boolean handled) {
		this.handled = handled;
	}

}
