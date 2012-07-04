package org.ssg.gui.client.topic.view;

import org.ssg.gui.client.topic.presenter.TopicInfoPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class TopicInfoView extends Composite implements
		TopicInfoPresenter.Display {

	private static TopicInfoViewUiBinder uiBinder = GWT
			.create(TopicInfoViewUiBinder.class);

	interface TopicInfoViewUiBinder extends UiBinder<Widget, TopicInfoView> {
	}

	@UiField
	HasText topicName;

	@UiField
	HasText topicDescription;

	@UiField
	HasText topicProgress;

	public TopicInfoView(RootPanel rootPanel) {
		initWidget(uiBinder.createAndBindUi(this));
		rootPanel.add(this);
	}

	public HasText getTopicName() {
		return topicName;
	}

	public HasText getTopicDescription() {
		return topicDescription;
	}

	public HasText getTopicProgress() {
		return topicProgress;
	}

}
