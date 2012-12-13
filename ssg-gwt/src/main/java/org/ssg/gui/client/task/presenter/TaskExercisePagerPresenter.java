package org.ssg.gui.client.task.presenter;

import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.gui.client.task.event.OpenExerciseEvent;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.event.UpdateTaskInfoEvent;

import com.google.gwt.event.shared.HandlerManager;

public class TaskExercisePagerPresenter implements UpdateTaskInfoEvent.Handler, OpenTaskEvent.Handler {

	private HandlerManager handlerManager;
	private TopicTaskDetailedInfo taskInfo;
	private boolean openFirstExercise;

	protected TaskExercisePagerPresenter(HandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}

	public void bind() {
		handlerManager.addHandler(OpenTaskEvent.TYPE, this);
		handlerManager.addHandler(UpdateTaskInfoEvent.TYPE, this);
	}

	public void onTaskInfoUpdate(TopicTaskDetailedInfo taskInfo) {
		this.taskInfo = taskInfo;
		Integer exerciseId = null;
		
		if (openFirstExercise) {
			exerciseId = taskInfo.findFirstExercise();
		} else {
			// TODO: get provided exercise id
		}
		
		if (exerciseId == null) {
			// TODO: show error message
		} else {
			openExercise(exerciseId);
		}
			
	}

	private void openExercise(int exerciseId) {
		handlerManager.fireEvent(new OpenExerciseEvent(exerciseId));
	}

	public void onOpenTaskEvent(OpenTaskEvent event) {
		if (event.isExerciseProvided()) {
			// TODO: set exercise id to be opened
		} else {
			openFirstExercise = true;
		}
	}

}
