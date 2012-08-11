package org.ssg.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeworkInfo implements Serializable {
	private static final long serialVersionUID = -7613399945041732901L;

	private int id;
	private String assignedModules;
	private ArrayList<TopicProgressInfo> topicProgress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssignedModules() {
		return assignedModules;
	}

	public void setAssignedModules(String assignedModules) {
		this.assignedModules = assignedModules;
	}

	public ArrayList<TopicProgressInfo> getTopicProgress() {
		return topicProgress;
	}

	public void setTopicProgress(ArrayList<TopicProgressInfo> topicProgress) {
		this.topicProgress = topicProgress;
	}

}