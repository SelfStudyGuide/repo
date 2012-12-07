package org.ssg.core.dto;

import java.util.ArrayList;

public class HomeworkDetailedInfo extends HomeworkInfo {
    private static final long serialVersionUID = -981716449825731628L;

    private ArrayList<TopicProgressInfo> topicProgress;
	
	public HomeworkDetailedInfo(){
	    super();
	    topicProgress = new ArrayList<TopicProgressInfo>();
    }

	public ArrayList<TopicProgressInfo> getTopicProgress() {
		return topicProgress;
	}

	public void setTopicProgress(ArrayList<TopicProgressInfo> topicProgress) {
		this.topicProgress = topicProgress;
	}
}
