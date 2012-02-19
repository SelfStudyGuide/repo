package org.ssg.gui.server.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface StudentUIService {
	
	@PreAuthorize("hasRole('teller')")
	<T extends Response> T execute(Action<T> action);
}
