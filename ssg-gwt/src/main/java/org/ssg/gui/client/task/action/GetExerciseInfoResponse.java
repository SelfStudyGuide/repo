package org.ssg.gui.client.task.action;

import java.io.Serializable;

import org.ssg.core.dto.ExerciseInfo;
import org.ssg.gui.client.action.Response;

public class GetExerciseInfoResponse implements Response, Serializable {

	private static final long serialVersionUID = -5437075750615898493L;

	private ExerciseInfo exerciseInfo;

	protected GetExerciseInfoResponse() {
    }

	public GetExerciseInfoResponse(ExerciseInfo exerciseInfo) {
		this.exerciseInfo = exerciseInfo;
	}

	public ExerciseInfo getExerciseInfo() {
		return exerciseInfo;
	}

}
