package org.ssg.gui.server.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface StudentUIService {
	<T extends Response> T execute(Action<T> action);
}
