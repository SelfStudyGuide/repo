package org.ssg.gui.client.topic.view;

import java.util.HashMap;

import org.ssg.gui.client.service.res.StudentHomeResources;
import org.ssg.gui.client.topic.presenter.TaskListPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class TaskListView extends Composite implements TaskListPresenter.Display {

	private static TaskListViewUiBinder uiBinder = GWT.create(TaskListViewUiBinder.class);
	private final RootPanel panel;

	interface TaskListViewUiBinder extends UiBinder<Widget, TaskListView> {
	}

	@UiField
	HorizontalPanel topicTaskButtons;

	private HashMap<Integer, Anchor> anchors;

	public TaskListView(RootPanel panel) {
		this.panel = panel;
		anchors = new HashMap<Integer, Anchor>();
		initWidget(uiBinder.createAndBindUi(this));
	}

	public HasText getTask(int id, String href) {
		Anchor anchor;
		if (anchors.containsKey(id)) {
			anchor = anchors.get(id);
		} else {
			anchor = new Anchor();
			anchor.setHref(href);
			anchor.setStyleName(StudentHomeResources.INSTANCE.styles().clickButton());
			anchors.put(id, anchor);
			SimplePanel p = new SimplePanel(anchor);
			
			p.setStyleName(StudentHomeResources.INSTANCE.styles().topicTaskListContainer());
			topicTaskButtons.add(p);
		}
		return anchor;
	}

	public void go() {
		panel.add(this);
	}

}
