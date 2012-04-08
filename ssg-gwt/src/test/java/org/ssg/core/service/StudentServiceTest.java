package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
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
	public void verifyThatHomeworkInfoCanBeLoadedByStudentId() {
		// given
		Student student = TstDataUtils.createStudent("studen with homework");
		Homework homework = TstDataUtils.createHomework(student, TstDataUtils.createModule());
		homework.setId(2);
		when(userDao.getStudentById(eq(123))).thenReturn(student);
		
		// when
		Collection<HomeworkInfo> hwList = studentSerice.getHomeworks(123);
		
		// then
		verify(userDao).getStudentById(eq(123));
		assertThat(hwList, notNullValue());
		assertThat(hwList.size(), is(1));
		HomeworkInfo hw = hwList.iterator().next();
		assertThat(hw.getAssignedModules(), equalTo("name"));
	}
	
	@Test
	public void verifyThatHomeworkDetailsCanBeLoadedById() {
		// given
		Student student = TstDataUtils.createStudent("studen with homework");
		Module module = TstDataUtils.enrichModuleWithTopics(TstDataUtils.createModule());
		Homework homework = TstDataUtils.createHomework(student, module);
		homework.setId(234);
		homework = TstDataUtils.enrichHomeworkWithProgress(homework, module.getTopics());
		
		when(homeworkDao.getHomework(234)).thenReturn(homework);
		
		// when
		HomeworkInfo details = studentSerice.getHomeworksDetails(234);
		
		// then
		verify(homeworkDao).getHomework(234);
		assertThat(details.getId(), is(234));
		assertThat(details.getAssignedModules(), equalTo("name"));
		assertThat(details.getTopicProgress().size(), is(3));
		
	}

	@Test(expected = SsgServiceException.class)
	public void verifyThatExceptionIsThrownIfNoHomeworkFound() {
		// given
		when(homeworkDao.getHomework(234)).thenReturn(null);
		
		// when
		studentSerice.getHomeworksDetails(234);
	}
	
	@Test(expected = SsgServiceException.class)
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
	
	@Test
	public void verifyThatStudentIdCanBeLoadedByItsUserName() {
		Student student = TstDataUtils.createStudent("John");
		student.setId(123);
		
		when(userDao.getStudentByName(eq("john"))).thenReturn(student);
		
		int id = studentSerice.getStudentIdByName("john");
		
		Assert.assertThat(id, is(123));
		
		verify(userDao).getStudentByName(eq("john"));
		
	}
}
