package org.ssg.core.dto;

public class TopicTaskDetailedInfo extends TopicTaskInfo {
    private static final long serialVersionUID = 2850062161473632503L;
    
	private Integer[] exerciseIds;
    
    public Integer[] getExerciseIds() {
		return exerciseIds;
	}

	public void setExerciseIds(Integer[] exerciseIds) {
		this.exerciseIds = exerciseIds;
	}
    
    public Integer findFirstExercise() {
		if (exerciseIds != null && exerciseIds.length > 0) {
			return exerciseIds[0];
		}
		return null;
	}

}
