package org.ssg.core.service;

import org.ssg.core.domain.Homework;

public interface HomeworkDao {

	void saveHomework(Homework homework);

	/**
	 * Loads Homework from database by its id. Null if nothing found.
	 */
	Homework getHomework(int homeworkId);

}
