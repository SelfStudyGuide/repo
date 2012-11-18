package org.ssg.core.dto;

import java.io.Serializable;

public class TopicTaskInfo implements Serializable {

	private static final long serialVersionUID = 7363754530636194857L;

	private int id;
	private TaskType type;
	private int execrisesCount;
	private int completedCount;
	private Integer[] exerciseIds;
	private int homeworkId;

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

	public Integer[] getExerciseIds() {
		return exerciseIds;
	}

	public void setExerciseIds(Integer[] exerciseIds) {
		this.exerciseIds = exerciseIds;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
	}

	public Integer findFirstExercise() {
		if (exerciseIds != null && exerciseIds.length > 0) {
			return exerciseIds[0];
		}
		return null;
	}

}
