package org.ssg.gui.client.topic.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;

public class GetTopicInfo extends BaseAction<GetTopicInfoResponse> implements Serializable {
	private static final long serialVersionUID = 3362046547360460874L;
	
	private int homeworkId;
	private int topicId;

	public GetTopicInfo(int homeworkId, int topicId) {
		this.homeworkId = homeworkId;
		this.topicId = topicId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public int getTopicId() {
		return topicId;
	}
}
