package org.ssg.core.support;

import static org.hamcrest.CoreMatchers.is;

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
import org.ssg.core.domain.Topic;
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
		//template.setFlushMode(HibernateAccessor.FLUSH_EAGER);
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

	protected Module getSavedModule() {
		Collection<Module> modules = curriculumDao.getAllModules();
		Assert.assertThat("Expected one module has been saved", modules.size(),
				is(1));
		return getFirstElement(modules);
	}

	protected Topic getSavedTopic() {
		Module savedModule = getSavedModule();
		List<Topic> savedTopics = savedModule.getTopics();
		Assert.assertThat("Expected one topic has been saved",
				savedTopics.size(), is(1));
		return getFirstElement(savedTopics);
	}
	
	protected Student getSavedStudent() {
		List<Student> list = userDao.getAllStudents();
		Assert.assertThat("Expected one student has been saved",
				list.size(), is(1));
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
