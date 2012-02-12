package org.ssg.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.adapter.HomeworkAdapter;
import org.ssg.core.dto.HomeworkInfo;

@Service
@Transactional
public class DefaultStudentService implements StudentService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private HomeworkDao homeworkDao;

	@Autowired
	private CurriculumDao curriculumDao;

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

}
