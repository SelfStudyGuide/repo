package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Student;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		deleteAll(Student.class);
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
