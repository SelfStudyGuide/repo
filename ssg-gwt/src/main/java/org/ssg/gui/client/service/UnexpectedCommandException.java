package org.ssg.gui.client.service;

/**
 * 
 *
 */
public class UnexpectedCommandException extends SsgGuiServiceException {

	private static final long serialVersionUID = -7816342463626515691L;
	
	private String commandName;
	private String commandId;
	private String errorCode;
	
	public UnexpectedCommandException() {
		super();
	}

	/**
	 * 
	 * @param message exception message for debug
	 * @param errorCode message key to lookup on the client
	 * @param commandName 
	 * @param commandId
	 */
	public UnexpectedCommandException(String message, String errorCode, String commandName, String commandId) {
		super(message);
		this.errorCode = errorCode;
		this.commandName = commandName;
		this.commandId = commandId;
	}

	public String getCommandName() {
		return commandName;
	}

	public String getCommandId() {
		return commandId;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
}
