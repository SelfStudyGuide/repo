package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.ssg.core.common.SsgServiceException;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.service.DefaultStudentService;
import org.ssg.core.service.HomeworkDao;
import org.ssg.core.service.UserDao;
import org.ssg.core.support.TstDataUtils;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

	@Mock
	private UserDao userDao;

	@Mock
	private HomeworkDao homeworkDao;

	@Mock
	private CurriculumDao curriculumDao;

	@InjectMocks
	private DefaultStudentService studentSerice = new DefaultStudentService();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void verifyThatHomeworkCanBeLoadedByStudentId() {
		Student student = TstDataUtils.createStudent("studen with homework");
		Homework homework = TstDataUtils.createHomework(student, null);
		homework.setId(2);

		when(userDao.getStudentById(eq(123))).thenReturn(student);
		Collection<HomeworkInfo> hw = studentSerice.getHomeworks(123);
		Assert.assertThat(hw, notNullValue());
		Assert.assertThat(hw.size(), is(1));
		Assert.assertThat(hw.iterator().next().getId(), is(2));

		verify(userDao).getStudentById(eq(123));
	}

	@Test(expected = SsgServiceException.class)
	@Ignore
	public void verifyThatExceptionIsThrownIfNoStudentFound() {
		when(userDao.getStudentById(eq(124))).thenReturn(null);

		Collection<HomeworkInfo> hw = studentSerice.getHomeworks(124);
	}

	@Test
	public void verifyThatHomeworkCanBeGaveToStudent() {
		Student student = TstDataUtils.createStudent("studen");
		Module module = TstDataUtils.createModule();
		when(userDao.getStudentById(eq(1))).thenReturn(student);
		when(curriculumDao.getModuleById(eq(2))).thenReturn(module);

		studentSerice.giveHomework(1, 2);

		ArgumentCaptor<Homework> homeworkCaptor = ArgumentCaptor
				.forClass(Homework.class);
		verify(homeworkDao).saveHomework(homeworkCaptor.capture());

		Homework homework = homeworkCaptor.getValue();
		Assert.assertThat(homework.getStudent(), is(student));
		Assert.assertThat(homework.getModules().size(), is(1));
		Assert.assertThat(homework.getModules().get(0), is(module));

	}
}
