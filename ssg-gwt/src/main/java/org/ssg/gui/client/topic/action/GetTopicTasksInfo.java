package org.ssg.gui.client.topic.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;

public class GetTopicTasksInfo extends BaseAction<GetTopicTasksInfoResponse> implements Serializable {

	private static final long serialVersionUID = 497658311755941451L;

	private int homeworkId;
	private int topicId;

	/**
	 * Used by GWT RPC
	 */
	@SuppressWarnings("unused")
	private GetTopicTasksInfo() {
		super();
	}

	public GetTopicTasksInfo(int homeworkId, int topicId) {
		this.homeworkId = homeworkId;
		this.topicId = topicId;
	}

	public int getHomeworkId() {
		return homeworkId;
	}

	public int getTopicId() {
		return topicId;
	}

	@Override
	public String toString() {
		return "GetTopicTasksInfo [homeworkId=" + homeworkId + ", topicId=" + topicId + "]";
	}

}
