package org.ssg.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.domain.adapter.HomeworkAdapter;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.server.ApplicationMessageSource;

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

	private MessageSourceAccessor applicationMsg = ApplicationMessageSource.getAccessor();

	public List<HomeworkInfo> getHomeworks(int userId) {
		LOG.info("Loading list of homeworks for user with id " + userId);
		Student student = userDao.getStudentById(userId);

		if (student == null) {
			throw new SsgServiceException(applicationMsg.getMessage("ssg.student.notfound",
			        new String[] { String.valueOf(userId) }));
		}

		ArrayList<HomeworkInfo> result = new ArrayList<HomeworkInfo>(student.getHomeworks().size());

		for (Homework hw : student.getHomeworks()) {
			HomeworkInfo info = new HomeworkInfo();
			new HomeworkAdapter(hw, false).populate(info);
			result.add(info);
		}
		return result;
	}

	public void giveHomework(int studentId, int moduleId) {
		Student student = userDao.getStudentById(studentId);
		Module m = curriculumDao.getModuleById(moduleId);
		Homework homework = new Homework();
		homework.setModules(Arrays.asList(m));
		homework.setStudent(student);
		homework.initTopicProgress(m.getTopics());

		homeworkDao.saveHomework(homework);
	}

	public int getStudentIdByName(String name) {
		LOG.info("Loading student with name " + name);

		Student student = userDao.getStudentByName(name);
		if (student == null) {
			throw new SsgServiceException(applicationMsg.getMessage("ssg.auth.studentNotFoundByName",
			        new String[] { name }));
		}
		return student.getId();
	}

	public HomeworkInfo getHomeworksDetails(int homeworkId) {
		LOG.info("Loading homework with id " + homeworkId);

		Homework homework = homeworkDao.getHomework(homeworkId);

		if (homework == null) {
			throw new SsgServiceException(applicationMsg.getMessage("ssg.homework.notfound",
			        new String[] { String.valueOf(homeworkId) }));
		}

		HomeworkInfo info = new HomeworkInfo();
		new HomeworkAdapter(homework, true).populate(info);

		return info;
	}

}
