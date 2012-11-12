package org.ssg.gui.client.task.view;

import org.ssg.gui.client.task.presenter.TaskControllerPreseter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class TaskControllerView extends Composite implements Display {

	private static TaskControllerViewUiBinder uiBinder = GWT.create(TaskControllerViewUiBinder.class);
	private final RootPanel panel;

	@UiField
	public HasText taskLabel;

	@UiField
	public HasClickHandlers saveButton;

	@UiField
	public ForIsWidget execrisePanel;

	interface TaskControllerViewUiBinder extends UiBinder<Widget, TaskControllerView> {
	}

	public TaskControllerView(RootPanel panel) {
		this.panel = panel;
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HasText getTaskLabel() {
		return taskLabel;
	}

	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	public ForIsWidget getExercisePanel() {
		return execrisePanel;
	}

	public void go() {
		panel.add(this);
	}

}
