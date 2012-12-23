package org.ssg.gui.client.task.presenter;

import org.ssg.core.dto.ExerciseType;
import org.ssg.gui.client.task.view.ExerciseView;

public interface ExerciseViewProvider {

	/**
	 * Instantiates (if needed) pair presenter and view for given exercise type.
	 * 
	 * @return exercise view.
	 */
	ExerciseView getExerciseView(ExerciseType exerciseType);
}
