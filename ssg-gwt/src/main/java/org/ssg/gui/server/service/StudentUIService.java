package org.ssg.gui.server.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.SsgGuiServiceException;

public interface StudentUIService {

	// @PreAuthorize("hasRole('teller')")
	<T extends Response> T execute(Action<T> action) throws SsgGuiServiceException;
}
