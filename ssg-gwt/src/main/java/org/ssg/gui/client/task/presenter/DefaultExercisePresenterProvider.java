package org.ssg.gui.client.task.presenter;

import java.util.HashMap;

import org.ssg.core.dto.ExerciseType;
import org.ssg.gui.client.task.view.ExerciseView;

public class DefaultExercisePresenterProvider implements ExerciseViewProvider {

	private HashMap<ExerciseType, ExerciseView> map;
	private ExerciseView notFoundView;

	public DefaultExercisePresenterProvider(HashMap<ExerciseType, ExerciseView> map, ExerciseView notFoundView){
		this.map = map;
		this.notFoundView = notFoundView;
    }

	public ExerciseView getExerciseView(ExerciseType exerciseType) {
		ExerciseView result = map.get(exerciseType);
		return result != null ? result : notFoundView;
	}

}
