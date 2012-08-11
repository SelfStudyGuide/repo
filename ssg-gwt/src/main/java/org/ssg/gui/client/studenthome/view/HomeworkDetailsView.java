package org.ssg.gui.client.studenthome.view;

import org.ssg.gui.client.studenthome.presenter.HomeworkDetailsPresenter.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomeworkDetailsView extends Composite implements Display {

	private static HomeworkDetailsViewUiBinder uiBinder = GWT.create(HomeworkDetailsViewUiBinder.class);

	interface HomeworkDetailsViewUiBinder extends UiBinder<Widget, HomeworkDetailsView> {
	}

	@UiField
	public Label moduleName;
	@UiField
	public Label teacherName;
	@UiField
	public Label assignDate;
	@UiField
	public Label completeDate;
	@UiField
	public VerticalPanel topicList;

	public HomeworkDetailsView(RootPanel rootPanel) {
		initWidget(uiBinder.createAndBindUi(this));
		rootPanel.add(this);
		hide();
	}

	public void creanUpView() {
		topicList.clear();
	}

	public HasText getModuleName() {
		return moduleName;
	}

	public void addTopic(String name, String url) {
		topicList.add(new Anchor(name, url));
	}

	public HasText getAssignedDate() {
		return assignDate;
	}

	public HasText getCompleteDate() {
		return completeDate;
	}

	public HasText getTeacherName() {
		return teacherName;
	}

	public void hide() {
		setVisible(false);
	}

	public void show() {
		setVisible(true);
	}

}
