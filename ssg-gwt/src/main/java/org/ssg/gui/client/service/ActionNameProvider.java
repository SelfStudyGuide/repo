package org.ssg.gui.client.service;

import org.ssg.gui.client.action.Action;
import org.ssg.gui.client.action.Response;


public interface ActionNameProvider {
	<R extends Response> String getActionName(Action<R> a);
}
