package org.ssg.core.service;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;

@Service
@Transactional
@Deprecated
public class DefaultStudentService implements StudentService {

	private static final Log LOG = LogFactory.getLog(DefaultStudentService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private CurriculumDao curriculumDao;


	public void giveHomework(int studentId, int moduleId) {
		Student student = userDao.getStudentById(studentId);
		Module m = curriculumDao.getModuleById(moduleId);
		Homework homework = new Homework();
		homework.setModules(Arrays.asList(m));
		homework.setStudent(student);
		homework.initTopicProgress(m.getTopics());

		homeworkDao.saveHomework(homework);
	}

}
