package org.ssg.gui.client.topic.action;

import java.io.Serializable;

import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.gui.client.action.Response;

public class GetTopicInfoResponse implements Response, Serializable{
	private static final long serialVersionUID = -9075505874619206090L;

	private TopicDetailedProgressInfo info;
	
	public GetTopicInfoResponse(TopicDetailedProgressInfo info) {
		this.info = info;
	}

	public TopicDetailedProgressInfo getInfo() {
		return info;
	}
	
}
