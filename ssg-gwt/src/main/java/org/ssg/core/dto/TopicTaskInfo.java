package org.ssg.core.dto;

import java.io.Serializable;

public class TopicTaskInfo implements Serializable {

	private static final long serialVersionUID = 7363754530636194857L;

	private int id;
	private TaskType type;
	private int execrisesCount;
	private int completedCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public int getExecrisesCount() {
		return execrisesCount;
	}

	public void setExecrisesCount(int execrisesCount) {
		this.execrisesCount = execrisesCount;
	}

	public int getCompletedCount() {
		return completedCount;
	}

	public void setCompletedCount(int completedCount) {
		this.completedCount = completedCount;
	}

	public boolean isType(TaskType type) {
		return getType() == type;
	}

}
