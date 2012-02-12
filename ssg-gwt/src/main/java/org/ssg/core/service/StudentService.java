package org.ssg.core.service;

import java.util.Collection;

import org.ssg.core.dto.HomeworkInfo;

public interface StudentService {

	Collection<HomeworkInfo> getHomeworks(int userId);

	void giveHomework(int studentId, int moduleId);

}
