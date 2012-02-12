package org.ssg.core.service;

import org.ssg.core.domain.Homework;

public interface HomeworkDao {

	void saveHomework(Homework homework);

	Homework getHomework(int userId);

}
