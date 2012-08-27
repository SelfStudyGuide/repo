package org.ssg.gui.client.service;

/**
 * If any {@link RuntimeException} on server side then it is wrapped with
 * {@link UnexpectedCommandException}.
 */
public class UnexpectedCommandException extends SsgGuiServiceException {

	private static final long serialVersionUID = -7816342463626515691L;

	private String commandName;

    protected UnexpectedCommandException() {
		super();
	}

	/**
	 * 
	 * @param message
	 *            exception message for debug
	 * @param errorCode
	 *            message key to lookup on the client
	 * @param commandName
	 * @param commandId
	 */
	public UnexpectedCommandException(String message, String errorCode, String commandName) {
		super(message, errorCode);
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
