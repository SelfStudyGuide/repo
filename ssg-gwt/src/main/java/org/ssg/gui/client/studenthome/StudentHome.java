package org.ssg.gui.client.studenthome;

import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.errordialog.ErrorDialogMessages;
import org.ssg.gui.client.errordialog.view.ErrorDialogView;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.DefaultActionSender;
import org.ssg.gui.client.service.DefaultWindowLocation;
import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.res.StudentHomeResources;
import org.ssg.gui.client.studenthome.presenter.HomeworkDetailsPresenter;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter;
import org.ssg.gui.client.studenthome.view.HomeworkDetailsView;
import org.ssg.gui.client.studenthome.view.HomeworkView;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;
import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter;
import org.ssg.gui.client.userinfo.view.UserInfoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StudentHome implements EntryPoint {

	private final StudentControlServiceAsync studentService = GWT
			.create(StudentControlService.class);
	private final SsgMessages ssgMessages = GWT.create(SsgMessages.class);
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		
		((ServiceDefTarget)studentService).setServiceEntryPoint(GWT.getHostPageBaseURL() + "StudentHome/Student");
		
		StudentHomeResources.INSTANCE.styles().ensureInjected();
		
		HandlerManager handlerManager = new HandlerManager(null);
		WindowLocation windowLocation = new DefaultWindowLocation();
		DefaultActionNameProvider nameProvider = new DefaultActionNameProvider();

		ErrorDialogView errorDialogView = new ErrorDialogView();
		ErrorDialogMessages errorDialogMessages = GWT
				.create(ErrorDialogMessages.class);
		ErrorDialog errorDialog = new ErrorDialog(errorDialogView,
				errorDialogMessages);

		DefaultActionSender actionSender = new DefaultActionSender(
				studentService, nameProvider, errorDialog, ssgMessages);

		HomeworkView homeworkView = new HomeworkView(ssgMessages);

		HomeworkPresenter homeworkPresenter = new HomeworkPresenter(
				homeworkView, actionSender, handlerManager, errorDialog);
		homeworkView.go(RootPanel.get("homework"));
		homeworkPresenter.bind();

		UserInfoView userInfoView = new UserInfoView();
		UserInfoPresenter userInfoPresenter = new UserInfoPresenter(
				userInfoView, actionSender, handlerManager, windowLocation);

		userInfoView.go(RootPanel.get("userinfo"));
		userInfoPresenter.bind();

		HomeworkDetailsView detailsView = new HomeworkDetailsView(
				RootPanel.get("homeworkDetails"));

		HomeworkDetailsPresenter detailsPresenter = new HomeworkDetailsPresenter(
				detailsView, ssgMessages, windowLocation, actionSender,
				handlerManager);
		detailsPresenter.bind();

		handlerManager.fireEvent(new RetrieveUserInfoEvent());
	}
}
