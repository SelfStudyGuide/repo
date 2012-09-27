package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

//@ActiveProfiles({"junit", "real-core", "junit-mock-security"})
//@ContextConfiguration(locations = { "classpath:/spring/test-application.ctx.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		deleteAll(Homework.class);
		// deleteAll(TopicProgress.class);
		deleteAll(Student.class);
		// deleteAll(Topic.class);
		deleteAll(Module.class);
	}

	private Student createStudentAndSave() {
		Student st = TstDataUtils.createStudent("name");
		userDao.saveStudent(st);
		return st;
	}

	@Test
	public void verifyThatStudentCanBeSaved() {
		createStudentAndSave();

		Student savedStudent = getSavedStudent();

		Assert.assertThat(savedStudent.getId(), not(0));
		Assert.assertThat(savedStudent.getName(), equalTo("name"));
		Assert.assertThat(savedStudent.getEmail(), equalTo("student@email.com"));

	}

	@Test
	public void verifyThatStudentCanBeLoadedById() {
		createStudentAndSave();
		Student savedStudent = getSavedStudent();

		clearSession();

		Student loadedStudent = userDao.getStudentById(savedStudent.getId());
		Assert.assertThat(loadedStudent, notNullValue());
	}

	@Test
	@Ignore
	public void verifyThatHibernateExceptionIsThrowsIfNoUserFound() {
		Student student = userDao.getStudentById(1);
		Assert.assertThat(student, nullValue());

	}

	@Test
	public void verifyThatStudentCanBeLoadedByItsName() {
		userDao.saveStudent(TstDataUtils.createStudent("John"));

		Student student = userDao.getStudentByName("John");

		Assert.assertThat(student.getName(), equalTo("John"));
	}


}
