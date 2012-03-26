package org.ssg.gui.client.action;

import java.io.Serializable;

public interface Action<T extends Response> extends Serializable {
	String getActionName();
}
