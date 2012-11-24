package org.ssg.gui.client.task.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;

public class GetExerciseInfo extends BaseAction<GetExerciseInfoResponse> implements Serializable {

	private static final long serialVersionUID = 7922887596237022379L;

	private int exerciseId;

	private int homeworkId;

	protected GetExerciseInfo() {
	}

	public GetExerciseInfo(int homeworkId, int exerciseId) {
		this.homeworkId = homeworkId;
		this.exerciseId = exerciseId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

}
