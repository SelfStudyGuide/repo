package org.ssg.core.dto;

import java.io.Serializable;

public class ModuleInfo implements Serializable {
	private static final long serialVersionUID = -3728520193914000180L;

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
