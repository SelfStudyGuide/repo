package org.ssg.gui.client.task.presenter;

import org.ssg.core.dto.ExerciseInfo;
import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.DefaultActionSender;
import org.ssg.gui.client.task.action.GetExerciseInfo;
import org.ssg.gui.client.task.action.GetExerciseInfoResponse;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.task.event.DisplayExerciseEvent;
import org.ssg.gui.client.task.event.OpenExerciseEvent;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.event.UpdateTaskInfoEvent;
import org.ssg.gui.client.task.view.ExerciseView;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;

public class TaskControllerPresenter implements OpenTaskEvent.Handler, UpdateTaskInfoEvent.Handler,
        OpenExerciseEvent.Handler {

	private HandlerManager handlerManager;
	private DefaultActionSender actionSender;
	private Display view;
	private SsgMessages ssgMessages;
	//private TaskExercisePagerPresenter exercisePagerPresenter;
	private ExerciseViewProvider exercisePresenterProvider;
	private boolean openFirstExercise;
	private TopicTaskDetailedInfo taskInfo;

	public interface Display {
		HasText getTaskLabel();

		HasClickHandlers getSaveButton();

		ForIsWidget getExercisePanel();
	}

	public TaskControllerPresenter(Display view, SsgMessages ssgMessages, DefaultActionSender actionSender,
	        HandlerManager handlerManager, ExerciseViewProvider exercisePresenterFactory){
		this.view = view;
		this.ssgMessages = ssgMessages;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
		this.exercisePresenterProvider = exercisePresenterFactory;
		//exercisePagerPresenter = new TaskExercisePagerPresenter(handlerManager);
	}

	public void bind() {
		handlerManager.addHandler(OpenTaskEvent.TYPE, this);
		handlerManager.addHandler(UpdateTaskInfoEvent.TYPE, this);
		handlerManager.addHandler(OpenExerciseEvent.TYPE, this);
		//exercisePagerPresenter.bind();
	}

	public void onOpenTaskEvent(OpenTaskEvent event) {
		if (event.isExerciseProvided()) {
			// TODO: set exercise id to be opened
		} else {
			openFirstExercise = true;
		}

		actionSender.send(new GetTaskInfo(event.getHomeworkId(), event.getTaskId()),
		        new ActionCallbackAdapter<GetTaskInfoResponse>() {

			        @Override
			        public void onResponse(GetTaskInfoResponse response) {
				        handlerManager.fireEvent(new UpdateTaskInfoEvent(response.getTaskInfo()));
			        }

		        });
	}

	protected void processTaskInfoResponse(TopicTaskDetailedInfo taskInfo) {

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

		String taskLabel = ssgMessages.taskViewTaskLabel(taskInfo.getType().toString());
		view.getTaskLabel().setText(taskLabel);
	}

	public void onTaskInfoUpdate(TopicTaskDetailedInfo taskInfo) {
		processTaskInfoResponse(taskInfo);

	}

	private void openExercise(int exerciseId) {
		handlerManager.fireEvent(new OpenExerciseEvent(taskInfo.getHomeworkId(), exerciseId));
	}

	public void onOpenExerciseEvent(OpenExerciseEvent event) {
		GetExerciseInfo action = new GetExerciseInfo(event.getHomeworkId(), event.getExerciseId());
		actionSender.send(action, new ActionCallbackAdapter<GetExerciseInfoResponse>() {

			@Override
			public void onResponse(GetExerciseInfoResponse response) {
				view.getExercisePanel().clear();
				ExerciseInfo exerciseInfo = response.getExerciseInfo();
				ExerciseView exerciseView = exercisePresenterProvider.getExerciseView(exerciseInfo.getExerciseType());
				exerciseView.go(view.getExercisePanel());
				handlerManager.fireEvent(new DisplayExerciseEvent(exerciseInfo));
			}

		});
	}

}
