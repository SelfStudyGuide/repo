package org.ssg.gui.client.task.presenter;

import java.util.HashMap;

import org.ssg.core.dto.ExerciseType;
import org.ssg.gui.client.task.view.ExerciseView;

public class DefaultExerciseViewProvider implements ExerciseViewProvider {

	private HashMap<ExerciseType, ExerciseView> map;
	private ExerciseView notFoundView;

	public DefaultExerciseViewProvider(HashMap<ExerciseType,ExerciseView> hashMap, ExerciseView notFoundView){
		this.map = hashMap;
		this.notFoundView = notFoundView;
    }

	public ExerciseView getExerciseView(ExerciseType exerciseType) {
		ExerciseView result = map.get(exerciseType);
		return result != null ? result : notFoundView;
	}

}
