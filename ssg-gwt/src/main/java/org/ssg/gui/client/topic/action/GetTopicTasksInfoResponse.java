package org.ssg.gui.client.topic.action;

import java.io.Serializable;
import java.util.List;

import org.ssg.core.dto.TopicTaskInfo;
import org.ssg.gui.client.action.Response;

public class GetTopicTasksInfoResponse implements Response, Serializable {

	private static final long serialVersionUID = 3033394799406497182L;

	private List<TopicTaskInfo> taskInfos;
	
	// GWT PRC
	@SuppressWarnings("unused")
	private GetTopicTasksInfoResponse() {
	}
	
	public GetTopicTasksInfoResponse(List<TopicTaskInfo> taskInfos) {
		this.taskInfos = taskInfos;
	}

	public List<TopicTaskInfo> getTaskInfos() {
		return taskInfos;
	}
}
