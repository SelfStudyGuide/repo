package org.ssg.core.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Topic;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.shared.BaseAction;
import org.ssg.gui.shared.Response;

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
		// module.setHomeworks(Arrays.asList(homework));
		return homework;
	}

	public static HomeworkInfo createHomeworkInfo() {
		HomeworkInfo result = new HomeworkInfo();
		result.setId(1);
		return result;
	}

	public static ApplicationUserInfo createStudentUserInfo(String name,
			int studentId) {
		ApplicationUserInfo info = new ApplicationUserInfo();
		info.setUsername(name);
		info.setStudentId(studentId);
		return info;
	}

	public static ApplicationUser createAppUserDetails(String username,
			int studentId) {
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("student"));
		User user = new User(username, "", roles);
		ApplicationUser appUser = new ApplicationUser(user, studentId);
		return appUser;
	}

	public static class TestAction extends BaseAction<TestActionResponse> {

	}

	public static class TestActionResponse implements Response {

	}

	public static class TestActionCallback implements
			ActionResponseCallback<TestActionResponse> {

		public void onResponse(TestActionResponse response) {
		}

		public void onError(SsgGuiServiceException exception) {
		}
	}

	public static class TestActionCallbackWithAdapter extends
			ActionCallbackAdapter<TestActionResponse> {

		public void onResponse(TestActionResponse response) {
		}
	}
}
