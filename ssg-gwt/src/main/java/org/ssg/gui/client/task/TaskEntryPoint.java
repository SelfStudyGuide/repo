package org.ssg.gui.client.task;

import static org.ssg.gui.client.service.res.UrlContants.HOMEWORK_ID;
import static org.ssg.gui.client.service.res.UrlContants.TASK_ID;

import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.task.event.OpenTaskEvent;
import org.ssg.gui.client.task.presenter.TaskControllerPresenter;
import org.ssg.gui.client.task.view.TaskControllerView;
import org.ssg.gui.client.userinfo.FireEventWhenUserInfo;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

import com.google.gwt.user.client.ui.RootPanel;

public class TaskEntryPoint extends BaseEntryPoint {

	@Override
	protected void initPageContent() {

		TaskControllerView view = new TaskControllerView(RootPanel.get("taskController"));
		TaskControllerPresenter preseter = new TaskControllerPresenter(view, getSsgMessages(), getActionSender(),
		        getHandlerManager());
		preseter.bind();
		view.go();

		fireTaskInfoEventWhenUserInfoReceived();
		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());

	}

	private void fireTaskInfoEventWhenUserInfoReceived() {
		FireEventWhenUserInfo userInfoToAction = new FireEventWhenUserInfo(getHandlerManager());

		userInfoToAction.setEvent(new OpenTaskEvent(getParameterInt(HOMEWORK_ID), getParameterInt(TASK_ID)));
	}

}
