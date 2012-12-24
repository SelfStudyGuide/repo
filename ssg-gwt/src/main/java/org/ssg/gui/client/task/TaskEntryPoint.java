package org.ssg.gui.client.task;

import static org.ssg.gui.client.service.res.UrlContants.HOMEWORK_ID;
import static org.ssg.gui.client.service.res.UrlContants.TASK_ID;

import java.util.HashMap;

import org.ssg.core.dto.ExerciseType;
import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.presenter.DefaultExerciseViewProvider;
import org.ssg.gui.client.task.presenter.ExerciseViewProvider;
import org.ssg.gui.client.task.presenter.TaskControllerPresenter;
import org.ssg.gui.client.task.view.ExerciseView;
import org.ssg.gui.client.task.view.NotFoundExerciseView;
import org.ssg.gui.client.task.view.TaskControllerView;
import org.ssg.gui.client.userinfo.FireEventWhenUserInfo;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

import com.google.gwt.user.client.ui.RootPanel;

public class TaskEntryPoint extends BaseEntryPoint {

	private TaskControllerView taskControllerView;

	@Override
	protected void initPageContent() {

		ExerciseViewProvider exercisePresenterProvider = initExercisePresenterProvider();

		taskControllerView = new TaskControllerView(RootPanel.get("taskController"));
		TaskControllerPresenter preseter = new TaskControllerPresenter(taskControllerView, getSsgMessages(), getActionSender(),
		        getHandlerManager(), exercisePresenterProvider);
		preseter.bind();
		taskControllerView.go();

		fireTaskInfoEventWhenUserInfoReceived();
		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());

	}

	private ExerciseViewProvider initExercisePresenterProvider() {
		NotFoundExerciseView notFoundExerciseView = new NotFoundExerciseView();
		HashMap<ExerciseType, ExerciseView> views = new HashMap<ExerciseType, ExerciseView>();
		return new DefaultExerciseViewProvider(views, notFoundExerciseView);
	}

	private void fireTaskInfoEventWhenUserInfoReceived() {
		FireEventWhenUserInfo userInfoToAction = new FireEventWhenUserInfo(getHandlerManager());

		userInfoToAction.setEvent(new OpenTaskEvent(getParameterInt(HOMEWORK_ID), getParameterInt(TASK_ID)));
	}

	public TaskControllerView getTaskControllerView() {
    	return taskControllerView;
    }
}
