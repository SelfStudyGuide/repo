package org.ssg.core.support.databuilder;

import org.ssg.core.dto.ExerciseInfo;
import org.ssg.core.dto.ExerciseType;

public class ExerciseInfoBuilder implements DataBuilder<ExerciseInfo> {

	private int id;
	private ExerciseType exerciseType;
	private int homeworkId;

	public static ExerciseInfoBuilder exerciseInfoBuilder() {
		return new ExerciseInfoBuilder();
	}

	public ExerciseInfoBuilder id(int id) {
		this.id = id;
		return this;
	}

	public ExerciseInfoBuilder exerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
		return this;
	}

	public ExerciseInfoBuilder homeworkId(int homeworkId) {
		this.homeworkId = homeworkId;
		return this;
	}

	public ExerciseInfo build() {
		ExerciseInfo info = new ExerciseInfo();
		info.setId(id);
		info.setExerciseType(exerciseType);
		info.setHomeworkId(homeworkId);
		return info;
	}

}
