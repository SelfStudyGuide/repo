package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.*;

import java.util.List;


import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@ContextConfiguration(locations = { "/test-config.ctx.xml", "/spring/core-service.ctx.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		deleteAll(Homework.class);
		deleteAll(Student.class);
		deleteAll(Module.class);
	}
	
	@Test
	public void verifyThatStudentCanBeSaved() {
		Student st = TstDataUtils.createStudent("name");

		userDao.saveStudent(st);

		Student savedStudent = getSavedStudent();

		Assert.assertThat(savedStudent.getId(), not(0));
		Assert.assertThat(savedStudent.getName(), equalTo("name"));
		Assert.assertThat(savedStudent.getEmail(), equalTo("student@email.com"));

	}

	@Test
	public void verifyThatStudentCanBeLoadedById() {
		Student st = TstDataUtils.createStudent("name");
		userDao.saveStudent(st);
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
	//@Rollback(value=false)
	public void verifyThatHomeworkCanBeAssignedToStudent() {
		Student st = TstDataUtils.createStudent("name");
		userDao.saveStudent(st);
		Module module = TstDataUtils.createModuleWithUniqueName();
		curriculumDao.saveModule(module);
		
		//clearSession();

		Student savedStudent = getSavedStudent();
		Module savedModule = getSavedModule();

		Homework homework = TstDataUtils.createHomework(savedStudent, savedModule);

		homeworkDao.saveHomework(homework);

		clearSession();
		
		Homework savedHomework = getSavedHomework();
		
		Assert.assertThat(savedHomework.getId(), not(0));
		Assert.assertThat(savedHomework.getStudent().getId(), is(savedStudent.getId()));
		Assert.assertThat(savedHomework.getModules().size(), is(1));
	}
	
	@Test
	public void verifyThatStudentCanBeLoadedByItsName() {
		userDao.saveStudent(TstDataUtils.createStudent("John"));
		
		Student student = userDao.getStudentByName("John");
		
		Assert.assertThat(student.getName(), equalTo("John"));
	}

	private Homework getSavedHomework() {
		List<Homework> all = template.loadAll(Homework.class);
		Assert.assertThat("Expected one homework has been saved", all.size(),
				is(1));
		return all.iterator().next();
	}

}
