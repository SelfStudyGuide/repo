package org.ssg.core.support;

import java.util.Arrays;

import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.dto.HomeworkInfo;

public class TstDataUtils {
	public static Module createModule() {
		Module module = new Module();
		module.setName("name");
		module.setDescription("desc");
		return module;
	}

	public static Module createModuleWithUniqueName() {
		Module module = TstDataUtils.createModule();
		module.setName("unique module name " + System.currentTimeMillis());
		return module;
	}

	public static Topic createTopicWithUniqueName() {
		Topic topic = new Topic();
		topic.setName("unique topic name " + System.currentTimeMillis());
		return topic;
	}

	public static Student createStudent(String name) {
		Student student = new Student();
		student.setName(name);
		student.setEmail("student@email.com");
		return student;
	}
	
	public static Homework createHomework(Student student, Module module) {
		Homework homework = new Homework();
		homework.setModules(Arrays.asList(module));
		homework.setStudent(student);
		student.setHomeworks(Arrays.asList(homework));
		//module.setHomeworks(Arrays.asList(homework));
		return homework;
	}
	
	public static HomeworkInfo createHomeworkInfo() {
		HomeworkInfo result = new HomeworkInfo();
		result.setId(1);
		return result;
	}
}
