package org.ssg.gui.shared;

import java.io.Serializable;

public interface Action<T extends Response> extends Serializable {
	String getActionName();
}
