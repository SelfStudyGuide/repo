package org.ssg.gui.client.studenthome;

import org.ssg.gui.client.service.BaseEntryPoint;
import org.ssg.gui.client.studenthome.presenter.HomeworkDetailsPresenter;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter;
import org.ssg.gui.client.studenthome.view.HomeworkDetailsView;
import org.ssg.gui.client.studenthome.view.HomeworkView;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;

import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StudentHome extends BaseEntryPoint {

	protected void initPageContent() {

		HomeworkView homeworkView = new HomeworkView(getSsgMessages());

		HomeworkPresenter homeworkPresenter = new HomeworkPresenter(
				homeworkView, getActionSender(), getHandlerManager(),
				getErrorDialog());
		homeworkView.go(RootPanel.get("homework"));
		homeworkPresenter.bind();

		HomeworkDetailsView detailsView = new HomeworkDetailsView(
				RootPanel.get("homeworkDetails"));
		HomeworkDetailsPresenter detailsPresenter = new HomeworkDetailsPresenter(
				detailsView, getSsgMessages(), getWindowLocation(),
				getActionSender(), getHandlerManager());
		detailsPresenter.bind();

		getHandlerManager().fireEvent(new RetrieveUserInfoEvent());
	}
}
