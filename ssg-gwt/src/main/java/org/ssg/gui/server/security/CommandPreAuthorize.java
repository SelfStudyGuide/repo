package org.ssg.gui.server.security;

import org.ssg.gui.client.action.Action;

public interface CommandPreAuthorize<A extends Action<?>> {
	void preAuthorize(A action) throws SsgSecurityException;
}
