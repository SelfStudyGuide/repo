package org.ssg.core.dto;

import java.io.Serializable;

public class TopicProgressInfo implements Serializable {
	private static final long serialVersionUID = 9121820558110681358L;

	private String status;
	private String topicName;
	private int topicId;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String name) {
		this.topicName = name;
	}

	public boolean isDone() {
		return "100".equals(getStatus());
	}

	public boolean isInProgress() {
		if (getStatus() == null || getStatus().isEmpty()) {
			return false;
		}

		int statusInt = Integer.parseInt(getStatus());
		return statusInt > 0 && statusInt < 100;
	}

	public boolean isNotStarted() {
		return getStatus() == null || getStatus().isEmpty() || "0".equals(getStatus());
	}

}
