package org.ssg.gui.client.service;

import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.errordialog.ErrorDialogMessages;
import org.ssg.gui.client.errordialog.view.ErrorDialogView;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.res.StudentHomeResources;
import org.ssg.gui.client.service.sender.DefaultActionResponseCallbackProcessor;
import org.ssg.gui.client.service.sender.DefaultActionSender;
import org.ssg.gui.client.userinfo.presenter.UserInfoPresenter;
import org.ssg.gui.client.userinfo.view.UserInfoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Base entry point which include common initialization.
 * 
 * @author Maksym
 *
 */
public abstract class BaseEntryPoint implements EntryPoint {

	private final StudentControlServiceAsync studentService = StudentControlServiceLocator
			.create();
	private final SsgMessages ssgMessages = GWT.create(SsgMessages.class);
	
	private final SsgLookupMessages ssgLookupMessages = GWT.create(SsgLookupMessages.class);
	
	private HandlerManager handlerManager;
	private WindowLocation windowLocation;
	private DefaultActionNameProvider nameProvider;
	private DefaultActionSender actionSender;
	private ErrorDialog errorDialog;
	private DefaultActionResponseCallbackProcessor processor;

	public final void onModuleLoad() {
		
		StudentHomeResources.INSTANCE.styles().ensureInjected();
		
		handlerManager = new HandlerManager(null);
		windowLocation = new DefaultWindowLocation();
		nameProvider = new DefaultActionNameProvider();

		createErrorDialog();
	
		processor = new DefaultActionResponseCallbackProcessor(errorDialog,
				ssgMessages, ssgLookupMessages);
		actionSender = new DefaultActionSender(studentService, nameProvider, processor);

		createUserInfoView();

		initPageContent();

	}

	private void createUserInfoView() {
		UserInfoView userInfoView = new UserInfoView();
		UserInfoPresenter userInfoPresenter = new UserInfoPresenter(
				userInfoView, actionSender, handlerManager, windowLocation);

		userInfoView.go(RootPanel.get("userinfo"));
		userInfoPresenter.bind();
	}

	private void createErrorDialog() {
		ErrorDialogView errorDialogView = new ErrorDialogView();
		ErrorDialogMessages errorDialogMessages = GWT
				.create(ErrorDialogMessages.class);
		errorDialog = new ErrorDialog(errorDialogView,
				errorDialogMessages);
	}

	protected abstract void initPageContent();

	public StudentControlServiceAsync getStudentService() {
		return studentService;
	}

	public SsgMessages getSsgMessages() {
		return ssgMessages;
	}

	public HandlerManager getHandlerManager() {
		return handlerManager;
	}

	public WindowLocation getWindowLocation() {
		return windowLocation;
	}

	public DefaultActionNameProvider getNameProvider() {
		return nameProvider;
	}

	public DefaultActionSender getActionSender() {
		return actionSender;
	}

	public ErrorDialog getErrorDialog() {
		return errorDialog;
	}

}
