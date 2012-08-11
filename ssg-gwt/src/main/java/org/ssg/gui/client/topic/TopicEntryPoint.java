package org.ssg.gui.client.topic;

import static org.ssg.gui.client.service.res.UrlContants.HOMEWORK_ID;
import static org.ssg.gui.client.service.res.UrlContants.TOPIC_ID;

import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;
import org.ssg.gui.client.topic.presenter.TaskListPresenter;
import org.ssg.gui.client.topic.presenter.TopicInfoPresenter;
import org.ssg.gui.client.topic.view.TaskListView;
import org.ssg.gui.client.topic.view.TopicInfoView;
import org.ssg.gui.client.userinfo.FireEventWhenUserInfo;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class TopicEntryPoint extends BaseEntryPoint {

	protected void initPageContent() {
		fireTopicInfoEventWhenUserInfoReceived();
		setupTopicInfoView();
		setupTopicTaskView();

		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());
	}

	private void setupTopicTaskView() {
		TaskListView view = new TaskListView(RootPanel.get("tasklist"));
		TaskListPresenter presenter = new TaskListPresenter(view, getHandlerManager(), getActionSender(),
		        getWindowLocation(), getSsgMessages());
		presenter.bind();
		view.go();
	}

	private void setupTopicInfoView() {
		TopicInfoView view = new TopicInfoView(RootPanel.get("topicinfo"));
		TopicInfoPresenter presenter = new TopicInfoPresenter(view, getActionSender(), getHandlerManager(),
		        getSsgMessages());
		presenter.bind();
	}

	private void fireTopicInfoEventWhenUserInfoReceived() {
		FireEventWhenUserInfo userInfoToAction = new FireEventWhenUserInfo(getHandlerManager());

		userInfoToAction.setEvent(new RefreshTopicInfoEvent(getParameterInt(HOMEWORK_ID), getParameterInt(TOPIC_ID)));
	}

	private int getParameterInt(String paramName) {
		String str = Window.Location.getParameter(paramName);
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			GWT.log("Incorrect int value for url param: " + paramName, e);
			return 0;
		}
	}

}
