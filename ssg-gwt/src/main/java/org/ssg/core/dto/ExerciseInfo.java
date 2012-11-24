package org.ssg.core.dto;

import java.io.Serializable;

public class ExerciseInfo implements Serializable {

	private static final long serialVersionUID = 1271980428351463872L;
	
	private int id;
	
	private ExerciseType exerciseType;
	
	private int homeworkId;

	public int getId() {
    	return id;
    }

	public void setId(int id) {
    	this.id = id;
    }

	public ExerciseType getExerciseType() {
    	return exerciseType;
    }

	public void setExerciseType(ExerciseType exerciseType) {
    	this.exerciseType = exerciseType;
    }

	public int getHomeworkId() {
    	return homeworkId;
    }

	public void setHomeworkId(int homeworkId) {
    	this.homeworkId = homeworkId;
    }
}
