package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataUtils;

@ContextConfiguration(locations = { "/test-config.ctx.xml", "/spring/core-service.ctx.xml" })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class HomeworkIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		deleteAll(Homework.class);
		// deleteAll(TopicProgress.class);
		deleteAll(Student.class);
		// deleteAll(Topic.class);
		deleteAll(Module.class);
	}
	
	@Test
	public void verifyThatHomeworkCanBeLoadedById() {
		Student savedStudent = createStudentAndSave();
		Module savedModule = createModuleAndSave();
		Homework savedHomework = createHomeworkAndSave(savedStudent, savedModule);
		clearSession();

		Homework loadedHomework = homeworkDao.getHomework(savedHomework.getId());

		Assert.assertThat(loadedHomework.getId(), is(savedHomework.getId()));
	}
	
	@Test
	// @Rollback(value=false)
	public void verifyThatHomeworkCanBeAssignedToStudent() {
		createStudentAndSave();
		createModuleAndSave();

		// clearSession();

		Student savedStudent = getSavedStudent();
		Module savedModule = getSavedModule();

		Homework homework = createHomeworkAndSave(savedStudent, savedModule);

		clearSession();

		Homework savedHomework = getSavedHomework();

		Assert.assertThat(savedHomework.getId(), not(0));
		Assert.assertThat(savedHomework.getStudent().getId(), is(savedStudent.getId()));
		assertThat(savedHomework.getModules().size(), is(1));

	}
	
	@Test
	// @Rollback(value=false)
	public void verifyThatTopicProgressCanBeSavedWithHomework() {
		createStudentAndSave();
		Module module = TstDataUtils.enrichModuleWithTopics(TstDataUtils.createModuleWithUniqueName());
		curriculumDao.saveModule(module);

		Student savedStudent = getSavedStudent();
		Module savedModule = getSavedModule();

		Homework homework = TstDataUtils.createHomework(savedStudent, savedModule);
		homework = TstDataUtils.enrichHomeworkWithProgress(homework, savedModule.getTopics());
		homeworkDao.saveHomework(homework);

		clearSession();

		Homework savedHomework = getSavedHomework();

		List<TopicProgress> savedProgress = savedHomework.getProgresses();
		assertThat(savedProgress.size(), is(3));
		assertThat(savedProgress.get(0).getHomework().getId(), is(savedHomework.getId()));
		assertThat(savedProgress.get(1).getHomework().getId(), is(savedHomework.getId()));
		assertThat(savedProgress.get(2).getHomework().getId(), is(savedHomework.getId()));
		assertThat(savedProgress.get(0).getTopic().getId(), is(savedModule.getTopics().get(0).getId()));
		assertThat(savedProgress.get(1).getTopic().getId(), is(savedModule.getTopics().get(1).getId()));
		assertThat(savedProgress.get(2).getTopic().getId(), is(savedModule.getTopics().get(2).getId()));
	}

	private Homework createHomeworkAndSave(Student savedStudent, Module savedModules) {
		Homework savedHomework = TstDataUtils.createHomework(savedStudent, savedModules);
		homeworkDao.saveHomework(savedHomework);
		return savedHomework;
	}

	private Module createModuleAndSave() {
		Module savedModule = TstDataUtils.createModuleWithUniqueName();
		curriculumDao.saveModule(savedModule);
		return savedModule;
	}

	private Student createStudentAndSave() {
		Student st = TstDataUtils.createStudent("name");
		userDao.saveStudent(st);
		return st;
	}
	
	private Homework getSavedHomework() {
		List<Homework> all = template.loadAll(Homework.class);
		Assert.assertThat("Expected one homework has been saved", all.size(), is(1));
		return all.iterator().next();
	}
}
