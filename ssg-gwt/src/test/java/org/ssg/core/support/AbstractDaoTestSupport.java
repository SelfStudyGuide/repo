package org.ssg.core.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.dto.TaskType;
import org.ssg.core.service.CurriculumDao;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.service.StudentService;
import org.ssg.core.service.UserDao;

public class AbstractDaoTestSupport {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	protected CurriculumDao curriculumDao;

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected HomeworkDao homeworkDao;

	@Autowired
	protected StudentService studentService;

	protected HibernateTemplate template;

	@Before
	public void setUpTest() {
		template = new HibernateTemplate(sessionFactory);
		// template.setFlushMode(HibernateAccessor.FLUSH_EAGER);
		cleanUpDb();
	}

	@After
	public void tearDown() {
		cleanUpDb();
	}

	protected void cleanUpDb() {
	}

	protected void deleteAll(Class<?> domainClassName) {
		try {
			List<?> list = template.loadAll(domainClassName);
			for (Object o : list) {
				template.delete(o);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	protected Task getSavedTask(TaskType type) {
		List<Task> all = template.loadAll(Task.class);
		for (Task task : all) {
			if (task.getType() == type) {
				return task;
			}
		}
		Assert.fail("No task with type: " + type + " saved");
		return null;
	}

	protected Module getSavedModule() {
		Collection<Module> modules = template.loadAll(Module.class);
		// Collection<Module> modules = curriculumDao.getAllModules();
		Assert.assertThat("Expected one module has been saved", modules.size(), is(1));
		return getFirstElement(modules);
	}

	protected Collection<Module> getSavedModules() {
		Collection<Module> modules = template.loadAll(Module.class);
		return modules;
	}

	protected Module[] getSavedModulesArray() {
		Collection<Module> modules = getSavedModules();
		return modules.toArray(new Module[modules.size()]);
	}

	protected void assertSavedTopics(int cnt) {
		assertThat("Expected Topics in DB", template.loadAll(Topic.class).size(), is(cnt));
	}

	protected Topic getSavedTopic() {
		// Module savedModule = getSavedModule();
		// List<Topic> savedTopics = savedModule.getTopics();
		List<Topic> savedTopics = template.loadAll(Topic.class);
		assertThat("Expected one topic has been saved", savedTopics.size(), is(1));
		return getFirstElement(savedTopics);
	}

	protected Student getSavedStudent() {
		List<Student> list = userDao.getAllStudents();
		Assert.assertThat("Expected one student has been saved", list.size(), is(1));
		return getFirstElement(list);
	}

	protected <T> T getFirstElement(Collection<T> col) {
		Iterator<T> iterator = col.iterator();
		Assert.assertThat("Collection should contain at least one element", iterator.hasNext(), is(true));
		return iterator.next();
	}

	protected void clearSession() {
		template.clear();
	}
}
