package org.ssg.gui.client.studenthome;

import org.ssg.gui.client.service.StudentControlService;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEvent;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter;
import org.ssg.gui.client.studenthome.view.HomeworkView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StudentHome implements EntryPoint {

	private final StudentControlServiceAsync studentService = GWT
			.create(StudentControlService.class);
	private final StudentHomeworkMessages homeworkMessages = GWT
			.create(StudentHomeworkMessages.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		HandlerManager handlerManager = new HandlerManager(null);

		HomeworkView homeworkView = new HomeworkView(homeworkMessages);

		HomeworkPresenter homeworkPresenter = new HomeworkPresenter(
				homeworkView, studentService, handlerManager);
		homeworkView.go(RootPanel.get("homework"));
		homeworkPresenter.bind();
		
		handlerManager.fireEvent(new RefreshStudentHomeworksEvent());

	}
}
