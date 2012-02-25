package org.ssg.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.adapter.HomeworkAdapter;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.server.ApplicationMessageSource;

@Service
@Transactional
public class DefaultStudentService implements StudentService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private CurriculumDao curriculumDao;
	
	private MessageSourceAccessor applicationMsg = ApplicationMessageSource.getAccessor();

	public Collection<HomeworkInfo> getHomeworks(int userId) {
		Student student = userDao.getStudentById(userId);
		// if (student == null) {
		// throw new SsgServiceException("No Homework found for student id="
		// + userId);
		// }

		ArrayList<HomeworkInfo> result = new ArrayList<HomeworkInfo>(student
				.getHomeworks().size());

		for (Homework hw : student.getHomeworks()) {
			HomeworkInfo info = new HomeworkInfo();
			new HomeworkAdapter(hw).populate(info);
			result.add(info);
		}
		return result;
	}

	public void giveHomework(int studentId, int moduleId) {
		Student student = userDao.getStudentById(studentId);
		Module module = curriculumDao.getModuleById(moduleId);
		Homework homework = new Homework();
		homework.setModules(Arrays.asList(module));
		homework.setStudent(student);

		homeworkDao.saveHomework(homework);
	}

	public int getStudentIdByName(String name) {
		Student student = userDao.getStudentByName(name);
		if (student == null) {
			throw new SsgServiceException(applicationMsg.getMessage(
					"ssg.auth.studentNotFoundByName", new String[] { name }));
		}
		return student.getId();
	}

}
