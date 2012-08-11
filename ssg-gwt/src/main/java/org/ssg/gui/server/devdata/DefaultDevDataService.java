package org.ssg.gui.server.devdata;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.dto.TaskType;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.StudentService;
import org.ssg.core.service.UserDao;

@Service(value = "DevDataService")
public class DefaultDevDataService {
	private static final Log LOG = LogFactory.getLog(DefaultDevDataService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private CurriculumDao curriculumDao;

	@Autowired
	private JdbcOperations jdbcOperations;

	@Autowired
	private StudentService studentService;

	@Transactional
	public void processRequest(Map<Object, Object> params, PrintWriter writer) {
		LOG.info("Creating dev data for request " + params);
		if (!params.isEmpty()) {
			dispatchRequest(params, writer);
		} else {
			printUsage(writer);
		}
	}

	private void dispatchRequest(Map<Object, Object> params, PrintWriter writer) {
		try {
			for (Object key : params.keySet()) {
				if (key.equals("user")) {
					processUser(params, writer);
				} else if (key.equals("module")) {
					processModule(params, writer);
				} else if (key.equals("homeworkForStudent")) {
					processHomework(params, writer);
				} else if (key.equals("taskForTopic")) {
					processTaskForTopic(params, writer);
				}
			}
		} catch (RuntimeException e) {
			LOG.error(params, e);
			writer.println("Error");
			writer.println("Request: " + params);
			e.printStackTrace(writer);
		}
	}

	private void printUsage(PrintWriter writer) {
		writer.println("To create Student. ?user=1");
		writer.println("To create Module. ?module=name&topics=2");
		writer.println("To create Homework. ?homeworkForStudent=1&moduleId=2");
		writer.println("To create Tasks. ?taskForTopic=1");
		writer.println("Get info for module. ?moduleInfo=1");
	}

	private void processHomework(Map<Object, Object> params, PrintWriter writer) {
		int studentId = getParamInt(params, "homeworkForStudent");
		int moduleId = getParamInt(params, "moduleId");

		studentService.giveHomework(studentId, moduleId);

		writer.println("Homework is created for student: " + studentId + ". Assigned module " + moduleId);
	}

	private void processModule(Map<Object, Object> params, PrintWriter writer) {
		String name = getParam(params, "module");
		String topicsCnt = getParam(params, "topics");

		Module module = new Module();
		module.setName("Module " + name);
		curriculumDao.saveModule(module);

		for (Module m : curriculumDao.getAllModules()) {
			if (module.getName().equals(m.getName())) {
				module = m;
				break;
			}
		}

		writer.println("Module Id: " + module.getId());

		if (topicsCnt != null) {
			for (int i = 0; i < Integer.parseInt(topicsCnt); i++) {
				Topic topic = new Topic();
				topic.setModule(module);
				topic.setName("Topic " + (i + 1));
				curriculumDao.saveTopic(topic);
			}
		}

		module = curriculumDao.getModuleById(module.getId());

		if (module.getTopics() != null) {
			for (Topic t : module.getTopics()) {
				writer.println("Topic Id: " + t.getId());
			}
		}

	}

	private void processUser(Map<Object, Object> params, PrintWriter writer) {
		String name = getParam(params, "user");
		Student student = new Student();
		student.setName(name);
		student.setEmail(name + "@email.com");
		userDao.saveStudent(student);
		jdbcOperations.execute("insert into users values('" + name + "','1',TRUE)");
		jdbcOperations.execute("insert into authorities values('" + name + "','student')");
		student = userDao.getStudentByName(name);
		writer.append("User Id: " + student.getId());
	}

	private void processTaskForTopic(Map<Object, Object> params, PrintWriter writer) {

		int topicId = getParamInt(params, "taskForTopic");
		Topic topic = curriculumDao.getTopic(topicId);
		if (topic != null) {
			Task task = new Task(topic);
			task.setType(TaskType.LEXICAL);
			task.setName(TaskType.LEXICAL.name());
			curriculumDao.saveTask(task);

			task = new Task(topic);
			task.setType(TaskType.LISTENING);
			task.setName(TaskType.LISTENING.name());
			curriculumDao.saveTask(task);

			task = new Task(topic);
			task.setType(TaskType.TEXT);
			task.setName(TaskType.TEXT.name());
			curriculumDao.saveTask(task);

		} else {
			throw new SsgServiceException("Topic " + topicId + " not found");
		}

	}

	private String getParam(Map<Object, Object> params, String name) {
		return params.containsKey(name) ? ((String[]) params.get(name))[0] : null;
	}

	private int getParamInt(Map<Object, Object> params, String name) {
		return Integer.parseInt(getParam(params, name));
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}

	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public CurriculumDao getCurriculumDao() {
		return curriculumDao;
	}

	public void setCurriculumDao(CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}
