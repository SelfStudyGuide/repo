package org.ssg.gui.client.task.action;

import java.io.Serializable;

import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.gui.client.action.Response;

public class GetTaskInfoResponse implements Response, Serializable {
	private static final long serialVersionUID = 1004224807399755720L;

	private TopicTaskInfo taskInfo;

	protected GetTaskInfoResponse() {
	}

	public GetTaskInfoResponse(TopicTaskInfo taskInfo) {
	    this.taskInfo = taskInfo;
    }

	public TopicTaskInfo getTaskInfo() {
		return taskInfo;
	}

}
