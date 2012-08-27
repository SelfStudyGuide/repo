package org.ssg.gui.server.security;

import org.ssg.core.domain.Homework;

/**
 * Provides server side authorization. Throws {@link SsgSecurityException} if
 * access deny.
 */
public interface Authorization {

	void ownStudent(int studentId) throws SsgSecurityException;
	
	void ownHomework(Homework homework) throws SsgSecurityException;

	void student() throws SsgSecurityException;
	
}
