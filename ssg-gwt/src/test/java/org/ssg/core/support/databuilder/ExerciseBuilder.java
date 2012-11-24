package org.ssg.core.support.databuilder;

import org.ssg.core.domain.Exercise;
import org.ssg.core.dto.ExerciseType;

public class ExerciseBuilder {

	public static ExerciseBuilder exerciseBuilder() {
		return new ExerciseBuilder();
	}

	private Exercise exercise = new Exercise();

	public Exercise build() {
		return exercise;
	}

	public ExerciseBuilder id(int id) {
		exercise.setId(id);
		return this;
	}

	public ExerciseBuilder description(String desc) {
		exercise.setDescription(desc);
		return this;
	}

	public ExerciseBuilder exerciseType(ExerciseType type) {
		exercise.setExerciseType(type);
		return this;
	}

	public ExerciseBuilder name(String name) {
		exercise.setName(name);
		return this;
	}

	public ExerciseBuilder order(int o) {
		exercise.setOrder(o);
		return this;
	}
}