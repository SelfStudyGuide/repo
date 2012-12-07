package org.ssg.core.dto;

import java.io.Serializable;

public class HomeworkInfo implements Serializable {
	private static final long serialVersionUID = -7613399945041732901L;

	private int id;
	private String assignedModules;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssignedModules() {
		return assignedModules;
	}

	public void setAssignedModules(String assignedModules) {
		this.assignedModules = assignedModules;
	}


}