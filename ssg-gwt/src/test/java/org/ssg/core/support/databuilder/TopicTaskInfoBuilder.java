package org.ssg.core.support.databuilder;

import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskInfo;

public class TopicTaskInfoBuilder implements DataBuilder<TopicTaskInfo> {

	private int id;
	private TaskType type;
	private int cnt;
	private int completedCnt;
	private Integer[] exerciseIds;
	
	public static TopicTaskInfoBuilder topicTaskInfoBuilder() {
		return new TopicTaskInfoBuilder();
	}

	public TopicTaskInfoBuilder exerciseIds(Integer... ids) {
		exerciseIds = ids;
		return this;
	}
	
	public TopicTaskInfoBuilder id(int id) {
		this.id = id;
		return this;
	}

	public TopicTaskInfoBuilder type(TaskType type) {
		this.type = type;
		return this;
	}

	public TopicTaskInfoBuilder execrisesCount(int cnt) {
		this.cnt = cnt;
		return this;
	}

	public TopicTaskInfoBuilder completedCount(int cnt) {
		completedCnt = cnt;
		return this;
	}
	
	public TopicTaskInfo build() {
		TopicTaskInfo result = new TopicTaskInfo();
		result.setId(id);
		result.setCompletedCount(completedCnt);
		result.setType(type);
		result.setExecrisesCount(cnt);
		result.setExerciseIds(exerciseIds);
	    return result;
    }

}
