package org.ssg.gui.client.service;

import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

public interface ActionNameProvider {
	<R extends Response> String getActionName(Action<R> a);
}
