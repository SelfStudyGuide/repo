package org.ssg.core.service;

import org.ssg.core.domain.Homework;

public interface HomeworkDao {

	void saveHomework(Homework homework);

	/**
	 * Loads Homework from database by its id.
	 * 
	 * @param homeworkId
	 */
	Homework getHomework(int homeworkId);

}
