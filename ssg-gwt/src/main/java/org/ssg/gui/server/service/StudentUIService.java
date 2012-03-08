package org.ssg.gui.server.service;

import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface StudentUIService {
	
	//@PreAuthorize("hasRole('teller')")
	<T extends Response> T execute(Action<T> action) throws SsgGuiServiceException;
}
