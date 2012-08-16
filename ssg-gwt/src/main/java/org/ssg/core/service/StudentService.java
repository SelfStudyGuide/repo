package org.ssg.core.service;

import java.util.Collection;

import org.ssg.core.dto.HomeworkInfo;

/**
 * @deprecated Actions should contain business logic.
 */

public interface StudentService {

	Collection<HomeworkInfo> getHomeworks(int userId);

	void giveHomework(int studentId, int moduleId);

	HomeworkInfo getHomeworksDetails(int homeworkId);

}
