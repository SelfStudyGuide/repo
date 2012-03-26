package org.ssg.gui.client.action;

public abstract class BaseAction<T extends Response> implements Action<T> {
	
	private static final long serialVersionUID = 4884435066002548047L;
	
	private String actionName;

	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String name) {
		this.actionName = name;
	}
}
