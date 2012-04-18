package org.ssg.core.dto;

import java.io.Serializable;

public class TopicProgressInfo implements Serializable {
	private static final long serialVersionUID = 9121820558110681358L;

	//private int id;
	private String status;
	private String name;
	private int topicId;
	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

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
		return name;
	}

	public void setTopicName(String name) {
		this.name = name;
	}

	public boolean isDone() {
		return "100".equals(getStatus());
	}

	public boolean isInProgress() {
		int statusInt = Integer.parseInt(getStatus());		
		return statusInt > 0 && statusInt < 100;
	}

	public boolean isNotStarted() {
		return "0".equals(getStatus());
	}

}
