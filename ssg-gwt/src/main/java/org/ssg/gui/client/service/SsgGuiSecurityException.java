package org.ssg.gui.client.service;

import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.sender.ActionResponseCallbackProcessor;

/**
 * Gui security exception class. This exception is handled by
 * {@link ActionResponseCallbackProcessor} class. Corresponded error message is
 * looked up from {@link SsgLookupMessages} by passed <code>errorCode</code>.
 */
public class SsgGuiSecurityException extends SsgGuiServiceException {

	private static final long serialVersionUID = -1357755282629174321L;
	
	protected SsgGuiSecurityException() {
    }

	public SsgGuiSecurityException(String message, String errorCode) {
		super(message, errorCode);
	}
}
