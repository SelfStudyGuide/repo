package org.ssg.core.service;

import java.util.Collection;

import org.ssg.core.common.SsgServiceException;
import org.ssg.core.dto.HomeworkInfo;

public interface StudentService {

	Collection<HomeworkInfo> getHomeworks(int userId);

	void giveHomework(int studentId, int moduleId);
	
	/**
	 * Returns student id by its name.
	 * @throws SsgServiceException if student not found
	 */
	int getStudentIdByName(String name) throws SsgServiceException;

	HomeworkInfo getHomeworksDetails(int homeworkId);
	
}
