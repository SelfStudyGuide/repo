package org.ssg.core.support.databuilder;

import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskDetailedInfo;

public class TopicTaskDetailedInfoBuilder extends TopicTaskInfoBuilder {
	
	protected int id;
	protected TaskType type;
	protected int cnt;
	protected int completedCnt;
	protected Integer[] exerciseIds;
	
	public static TopicTaskDetailedInfoBuilder topicTaskDetailedInfoBuilder() {
		return new TopicTaskDetailedInfoBuilder();
	}

	public TopicTaskDetailedInfoBuilder exerciseIds(Integer... ids) {
		exerciseIds = ids;
		return this;
	}
	
	public TopicTaskDetailedInfoBuilder id(int id) {
		this.id = id;
		return this;
	}

	public TopicTaskDetailedInfoBuilder type(TaskType type) {
		this.type = type;
		return this;
	}

	public TopicTaskDetailedInfoBuilder execrisesCount(int cnt) {
		this.cnt = cnt;
		return this;
	}

	public TopicTaskDetailedInfoBuilder completedCount(int cnt) {
		completedCnt = cnt;
		return this;
	}
	
	public TopicTaskDetailedInfo build() {
		TopicTaskDetailedInfo result = new TopicTaskDetailedInfo();
		result.setId(id);
		result.setCompletedCount(completedCnt);
		result.setType(type);
		result.setExecrisesCount(cnt);
		result.setExerciseIds(exerciseIds);
	    return result;
    }

}

