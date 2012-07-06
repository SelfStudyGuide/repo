package org.ssg.gui.client.topic;

import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;
import org.ssg.gui.client.topic.presenter.TopicInfoPresenter;
import org.ssg.gui.client.topic.view.TopicInfoView;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class TopicEntryPoint extends BaseEntryPoint {

	protected void initPageContent() {
		
		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());

		TopicInfoView topicInfoView = new TopicInfoView(
				RootPanel.get("topicinfo"));
		TopicInfoPresenter topicInfoPresenter = new TopicInfoPresenter(
				topicInfoView, getActionSender(), getHandlerManager(), getSsgMessages());
		topicInfoPresenter.bind();

		getHandlerManager().fireEvent(new RefreshTopicInfoEvent(
				getParameterInt("hid"), getParameterInt("tid")));

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
