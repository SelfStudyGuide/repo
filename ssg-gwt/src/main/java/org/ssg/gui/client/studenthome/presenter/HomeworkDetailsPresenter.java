package org.ssg.gui.client.studenthome.presenter;

import java.util.ArrayList;

import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionSender;
import org.ssg.gui.client.service.UserInfoHolder;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.studenthome.StudentHomeworkMessages;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;
import org.ssg.gui.client.studenthome.event.HomeworkSelectedEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

public class HomeworkDetailsPresenter implements HomeworkSelectedEvent.Handler {

	private static final String TOPIC_MODULE_NAME  = "Topic.html";
	
	public interface Display {

		void creanUpView();

		HasText getModuleName();
				
		void addTopic(String name, String url);

		HasText getAssignedDate();
		
		HasText getCompleteDate();
		
		HasText getTeacherName();
		
		void hide();
		
		void show();
	}

	private Display view;
	private HandlerManager handlerManager;
	private final ActionSender actionSender;
	private final StudentHomeworkMessages messages;
	private final WindowLocation windowLocation;

	public HomeworkDetailsPresenter(Display view,
			StudentHomeworkMessages messages, WindowLocation windowLocation,
			ActionSender actionSender, HandlerManager handlerManager) {
		this.view = view;
		this.messages = messages;
		this.windowLocation = windowLocation;
		this.actionSender = actionSender;
		this.handlerManager = handlerManager;
	}

	public void bind() {
		handlerManager.addHandler(HomeworkSelectedEvent.TYPE, this);
	}

	public void onHomeworkSelection(HomeworkInfo selected) {
		view.hide();
		actionSender.send(new GetHomeworkDetails(UserInfoHolder.getStudentId(),
				selected.getId()),
				new ActionCallbackAdapter<GetHomeworkDetailsResponse>() {

					@Override
					public void onResponse(GetHomeworkDetailsResponse response) {
						doUpdateDetails(response.getHomework());
					}

				});
	}

	protected void doUpdateDetails(HomeworkInfo homework) {
		view.creanUpView();
		view.getModuleName().setText(homework.getAssignedModules());
		view.getCompleteDate().setText("N/A");
		view.getAssignedDate().setText("N/A");
		view.getTeacherName().setText("N/A");
		updateTopics(homework);
		view.show();
	}

	private void updateTopics(HomeworkInfo homework) {
		ArrayList<TopicProgressInfo> topics = homework.getTopicProgress();
		if (topics != null && !topics.isEmpty()) {
			for (TopicProgressInfo topic : topics) {
				String topicName = null;
				if (topic.isDone()) {
					topicName = messages.homeworkDetailsDone(topic.getTopicName());
				} else if (topic.isInProgress()) {
					topicName = messages.homeworkDetailsInProgress(topic.getTopicName());
				} else if (topic.isNotStarted()) {
					topicName = messages.homeworkDetailsNotStarted(topic.getTopicName());
				} else {
					topicName = topic.getTopicName();
				}
				
				view.addTopic(
						topicName,
						windowLocation.getUrl(TOPIC_MODULE_NAME + "?id="
								+ homework.getId() + "_" + topic.getTopicId()));
			}
		}
	}
}
