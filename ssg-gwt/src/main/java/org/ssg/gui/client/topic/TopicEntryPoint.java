package org.ssg.gui.client.topic;

import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.errordialog.ErrorDialogMessages;
import org.ssg.gui.client.errordialog.view.ErrorDialogView;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.DefaultActionSender;
import org.ssg.gui.client.service.DefaultWindowLocation;
import org.ssg.gui.client.service.SsgMessages;
import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.service.res.StudentHomeResources;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;
import org.ssg.gui.client.topic.presenter.TopicInfoPresenter;
import org.ssg.gui.client.topic.view.TopicInfoView;
import org.ssg.gui.client.userinfo.event.RetrieveUserInfoEvent;
import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter;
import org.ssg.gui.client.userinfo.view.UserInfoView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

public class TopicEntryPoint implements com.google.gwt.core.client.EntryPoint {

	private final StudentControlServiceAsync studentService = GWT
			.create(StudentControlService.class);
	private final SsgMessages ssgMessages = GWT.create(SsgMessages.class);

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

		UserInfoView userInfoView = new UserInfoView();
		UserInfoPresenter userInfoPresenter = new UserInfoPresenter(
				userInfoView, actionSender, handlerManager, windowLocation);

		userInfoView.go(RootPanel.get("userinfo"));
		userInfoPresenter.bind();
		
		handlerManager.fireEvent(new RetrieveUserInfoEvent());

		TopicInfoView topicInfoView = new TopicInfoView(
				RootPanel.get("topicinfo"));
		TopicInfoPresenter topicInfoPresenter = new TopicInfoPresenter(
				topicInfoView, actionSender, handlerManager, ssgMessages);
		topicInfoPresenter.bind();

		handlerManager.fireEvent(new RefreshTopicInfoEvent(
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
