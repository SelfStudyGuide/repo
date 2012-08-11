package org.ssg.gui.client.topic.action;

import java.io.Serializable;

import org.ssg.gui.client.action.BaseAction;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetTopicInfo extends BaseAction<GetTopicInfoResponse> implements Serializable, IsSerializable {
	private static final long serialVersionUID = 3362046547360460874L;

	private int homeworkId;
	private int topicId;

	/**
	 * Used by GWT RPC
	 */
	@SuppressWarnings("unused")
	private GetTopicInfo() {
		super();
	}

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

	@Override
	public String toString() {
		return "GetTopicInfo [homeworkId=" + homeworkId + ", topicId=" + topicId + "]";
	}

}
