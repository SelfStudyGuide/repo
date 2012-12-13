package org.ssg.gui.client.task.action;

import java.io.Serializable;

import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.gui.client.action.Response;

public class GetTaskInfoResponse implements Response, Serializable {
	private static final long serialVersionUID = 1004224807399755720L;

	private TopicTaskDetailedInfo taskInfo;

	protected GetTaskInfoResponse() {
	}

	public GetTaskInfoResponse(TopicTaskDetailedInfo taskInfo) {
	    this.taskInfo = taskInfo;
    }

	public TopicTaskDetailedInfo getTaskInfo() {
		return taskInfo;
	}

}
