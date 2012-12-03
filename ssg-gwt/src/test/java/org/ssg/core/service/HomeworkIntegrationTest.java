package org.ssg.core.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.support.AbstractDaoTestSupport;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.core.support.TstDataUtils;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class HomeworkIntegrationTest extends AbstractDaoTestSupport {

	@Override
	protected void cleanUpDb() {
		deleteAll(Homework.class);
		deleteAll(Student.class);
		deleteAll(Module.class);
	}
	
	@Test
	// @Rollback(value=false)
	// TODO to delete
	public void verifyThatHomeworkCanBeAssignedToStudent() {
		createStudentAndSave();
		createModuleAndSave();

		// clearSession();

		Student savedStudent = getSavedStudent();
		Module savedModule = getSavedModule();

		Homework homework = createHomeworkAndSave(savedStudent, savedModule);

		//clearSession();

		Homework savedHomework = getSavedHomework();

		Assert.assertThat(savedHomework.getId(), not(0));
		Assert.assertThat(savedHomework.getStudent().getId(), is(savedStudent.getId()));
		assertThat(savedHomework.getModules().size(), is(1));

	}
	
	@Test
	// @Rollback(value=false)
	public void verifyThatTopicProgressCanBeSavedWithHomework() {
		// Given
		createStudentAndSave();
		Student savedStudent = getSavedStudent();
		Topic topic = TstDataBuilder.topicBuilder().name("name").build();
		curriculumDao.saveTopic(topic);
		
		// When
		TopicProgress topicProgress = TstDataBuilder.topicProgressBuilder().topic(topic).build();
		Homework homework = TstDataBuilder.homeworkBuilder().student(savedStudent).topicProgress(topicProgress).build();
		homeworkDao.saveHomework(homework);

		// Then
		Homework savedHomework = getSavedHomework();
		List<TopicProgress> savedProgress = savedHomework.getProgresses();
		assertThat(savedProgress.size(), is(1));
		assertThat(savedProgress.get(0).getHomework().getId(), is(savedHomework.getId()));
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
