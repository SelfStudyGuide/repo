package org.ssg.gui.client.task.presenter;

import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.DefaultActionSender;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.event.UpdateTaskInfoEvent;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;

public class TaskControllerPresenter implements OpenTaskEvent.Handler, UpdateTaskInfoEvent.Handler {

	private HandlerManager handlerManager;
	private DefaultActionSender actionSender;
	private Display view;
	private SsgMessages ssgMessages;
	private TaskExercisePagerPresenter exercisePagerPresenter;

	public interface Display {
		HasText getTaskLabel();

		HasClickHandlers getSaveButton();

		ForIsWidget getExercisePanel();
	}

	public TaskControllerPresenter(Display view, SsgMessages ssgMessages, DefaultActionSender actionSender,
	        HandlerManager handlerManager) {
		this.view = view;
		this.ssgMessages = ssgMessages;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
		exercisePagerPresenter = new TaskExercisePagerPresenter(handlerManager);
	}

	public void bind() {
		handlerManager.addHandler(OpenTaskEvent.TYPE, this);
		handlerManager.addHandler(UpdateTaskInfoEvent.TYPE, this);
		exercisePagerPresenter.bind();
	}

	public void onOpenTaskEvent(OpenTaskEvent event) {
		actionSender.send(new GetTaskInfo(event.getHomeworkId(), event.getTaskId()),
		        new ActionCallbackAdapter<GetTaskInfoResponse>() {

			        @Override
			        public void onResponse(GetTaskInfoResponse response) {
				        handlerManager.fireEvent(new UpdateTaskInfoEvent(response.getTaskInfo()));
			        }

		        });
	}

	protected void processTaskInfoResponse(TopicTaskInfo info) {
		String taskLabel = ssgMessages.taskViewTaskLabel(info.getType().toString());
		view.getTaskLabel().setText(taskLabel);
	}

	public void onTaskInfoUpdate(TopicTaskInfo taskInfo) {
		processTaskInfoResponse(taskInfo);
		
	}

	

}
