package org.ssg.gui.client.topic.presenter;

import static org.ssg.gui.client.service.res.UrlContants.HOMEWORK_ID;
import static org.ssg.gui.client.service.res.UrlContants.TASK_ID;
import static org.ssg.gui.client.service.res.UrlContants.TASK_PAGE;

import java.util.HashMap;
import java.util.Map;

import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.ActionSender;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;
import org.ssg.gui.client.topic.event.RefreshTopicInfoEvent;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasText;

public class TaskListPresenter implements RefreshTopicInfoEvent.Handler {

	private ActionSender actionSender;
	private HandlerManager handlerManager;
	private Display display;
	private WindowLocation windowLocation;
	private final SsgMessages messages;
	private Map<TaskType, String> typeToString;

	public interface Display {
		/**
		 * Returns task button. It creates new if no any with given id.
		 */
		HasText getTask(int id, String href);
	}

	public TaskListPresenter(Display display, HandlerManager handlerManager,
			ActionSender actionSender, WindowLocation windowLocation,
			SsgMessages messages) {
		this.handlerManager = handlerManager;
		this.actionSender = actionSender;
		this.display = display;
		this.windowLocation = windowLocation;
		this.messages = messages;
		initMapper();
	}

	private void initMapper() {
		typeToString = new HashMap<TaskType, String>();
		typeToString.put(TaskType.LEXICAL, messages.topicViewTaskLexical());
		typeToString.put(TaskType.TEXT, messages.topicViewTaskText());
		typeToString.put(TaskType.LISTENING, messages.topicViewTaskListening());
	}

	public void bind() {
		handlerManager.addHandler(RefreshTopicInfoEvent.TYPE, this);
	}

	public void onRefreshTopicInfo(final int homeworkId, int topicId) {
		actionSender.send(new GetTopicTasksInfo(homeworkId, topicId),
				new ActionCallbackAdapter<GetTopicTasksInfoResponse>() {

					public void onResponse(GetTopicTasksInfoResponse response) {
						for (TopicTaskInfo task : response.getTaskInfos()) {
							HasText taskBotton = display.getTask(task.getId(),
									getTaskPageHref(homeworkId, task.getId()));
							taskBotton.setText(getButtonLabel(task));
						}
					}

				});
	}

	private String getButtonLabel(TopicTaskInfo task) {
		String buttonName = typeToString.containsKey(task.getType()) ? typeToString
				.get(task.getType()) : task.getType().toString();
		return buttonName + " [" + task.getCompletedCount() + "/"
				+ task.getExecrisesCount() + "]";
	}

	private String getTaskPageHref(final int homeworkId, int taksId) {
		return windowLocation.getUrl(TASK_PAGE + "?" + HOMEWORK_ID + "="
				+ homeworkId + "&" + TASK_ID + "=" + taksId);
	}
}
