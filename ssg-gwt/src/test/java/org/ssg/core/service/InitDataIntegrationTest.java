package org.ssg.core.service;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.support.AbstractDaoTestSupport;

@ContextConfiguration(locations = { "/test-config.ctx.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class InitDataIntegrationTest extends AbstractDaoTestSupport {

	private static final int STUDENT_ID = 1;

	@Test
	public void verifyThatTheModuleCanBeLoaded() {
		Collection<Module> modules = curriculumDao.getAllModules();
		Module module = getFirstElement(modules);
		Assert.assertThat(module.getName(), equalTo("1st module"));
	}

	@Test
	public void verifyThatTheStudentCanBeLoaded() {
		List<Student> students = userDao.getAllStudents();
		Student student = getFirstElement(students);
		Assert.assertThat(student.getName(), equalTo("John Dou"));
		Assert.assertThat(student.getId(), is(STUDENT_ID));
	}

	@Test
	public void verifyThatGivedToStudentHomeworkCanBeLoaded() {
		Collection<HomeworkInfo> homeworks = studentService
				.getHomeworks(STUDENT_ID);
		Assert.assertThat(homeworks.size(), is(1));
		HomeworkInfo homework = homeworks.iterator().next();

		Assert.assertThat(homework.getId(), is(3));
		// Assert.assertThat(homework.getStudent().getId(), is(STUDENT_ID));
		// Assert.assertThat(homework.getModules().size(), is(1));

	}
}
