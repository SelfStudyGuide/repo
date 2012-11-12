package org.ssg.gui.client.task.presenter;

import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.DefaultActionSender;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.task.event.OpenTaskEvent;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;

public class TaskControllerPreseter implements OpenTaskEvent.Handler {

	private HandlerManager handlerManager;
	private DefaultActionSender actionSender;
	private Display view;
	private SsgMessages ssgMessages;
	
	
	public interface Display {
		HasText getTaskLabel();
		HasClickHandlers getSaveButton();
		ForIsWidget getExercisePanel();
	}

	public TaskControllerPreseter(Display view, SsgMessages ssgMessages, DefaultActionSender actionSender, HandlerManager handlerManager) {
		this.view = view;
		this.ssgMessages = ssgMessages;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
    }

	public void bind() {
		handlerManager.addHandler(OpenTaskEvent.TYPE, this);
    }

	public void onOpenTaskEvent(OpenTaskEvent event) {
	    actionSender.send(new GetTaskInfo(event.getHomeworkId(), event.getTaskId()), new ActionCallbackAdapter<GetTaskInfoResponse>() {

			@Override
            public void onResponse(GetTaskInfoResponse response) {
	            processTaskInfoResponse(response);
            }
	    	
	    });
    }

	protected void processTaskInfoResponse(GetTaskInfoResponse response) {
		String taskLabel = ssgMessages.taskViewTaskLabel(response.getTaskInfo().getType().toString());
		view.getTaskLabel().setText(taskLabel);
    }

}
