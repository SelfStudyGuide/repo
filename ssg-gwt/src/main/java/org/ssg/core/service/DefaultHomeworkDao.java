package org.ssg.core.service;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.dao.DefaultGenericDao;
import org.ssg.core.domain.Homework;

@Repository("HomeworkDao")
@Transactional
public class DefaultHomeworkDao extends DefaultGenericDao implements HomeworkDao {

	public void saveHomework(Homework homework) {
		save(homework);
	}

	public Homework getHomework(int homeworkId) {
		return getById(homeworkId, Homework.class);
	}
}
