package org.ssg.gui.server.security;

/**
 * Provides server side authorization. Throws {@link SsgSecurityException} if
 * access deny.
 */
public interface Authorization {

	void ownStudent(int studentId) throws SsgSecurityException;
	
	void ownHomework(int hwId) throws SsgSecurityException;

	void student() throws SsgSecurityException;
}
