package org.ssg.gui.client.task.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;
import org.ssg.gui.client.topic.action.GetTopicInfoResponse;

public class GetTaskInfo extends BaseAction<GetTaskInfoResponse> implements Serializable {

	private static final long serialVersionUID = 4212414168147834032L;

	private int homeworkId;
	private int taskId;

	protected GetTaskInfo() {
	}

	public GetTaskInfo(int homeworkId, int taskId) {
		this.homeworkId = homeworkId;
		this.taskId = taskId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public int getTaskId() {
		return taskId;
	}

	@Override
	public String toString() {
		return "GetTaskInfo [homeworkId=" + homeworkId + ", taskId=" + taskId + "]";
	}

}
