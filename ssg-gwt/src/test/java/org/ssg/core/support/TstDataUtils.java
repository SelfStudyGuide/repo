package org.ssg.core.support;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.dto.ModuleInfo;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicDetailedProgressInfo;
import org.ssg.core.dto.TopicProgressInfo;
import org.ssg.gui.client.action.BaseAction;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.ActionCallbackAdapter;
import org.ssg.gui.client.service.ActionResponseCallback;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.server.command.ActionHandler;

public class TstDataUtils {
	public static Module createModule() {
		Module module = new Module();
		module.setName("name");
		module.setDescription("desc");
		return module;
	}
	
	public static Module createModule(String name, int id) {
		Module module = createModule();
		module.setName(name);
		module.setId(id);
		return module;
	}

	@SuppressWarnings("serial")
	public static Module enrichModuleWithTopics(final Module module) {
		module.setTopics(new ArrayList<Topic>() {
			{
				add(createTopicWithUniqueName(module));
				add(createTopicWithUniqueName(module));
				add(createTopicWithUniqueName(module));
			}
		});
		return module;

	}

	public static Module enrichModuleWithId(Module mod) {
		mod.setId(123);
		return mod;
	}
	
	public static Module createModuleWithUniqueName() {
		Module module = TstDataUtils.createModule();
		module.setName("unique module name " + System.currentTimeMillis());
		return module;
	}

	@Deprecated
	public static Topic createTopicWithUniqueName(Module module) {
		Topic topic = new Topic();
		topic.setModule(module);
		topic.setName("unique topic name " + System.currentTimeMillis());
		return topic;
	}
	
	@Deprecated
	public static Topic createTopic(String name, int id) {
		Topic topic = createTopicWithUniqueName(null);
		topic.setName(name);
		topic.setId(id);
		topic.setDescription("Description of topic");
		return topic;
	}
	
	@Deprecated
	public static Task createTask(TaskType type) {
		Task task = new Task();
		task.setName(type.toString() + " task");
		task.setType(type);
		task.setDescription(type.toString() + " task descr");
		task.setExecrisesCount(2);
		return task;
	}

	public static Student createStudent(String name) {
		Student student = new Student();
		student.setName(name);
		student.setEmail("student@email.com");
		return student;
	}
	
	public static Student createStudent(int id, String name) {
		Student student = createStudent(name);
		student.setId(id);
		return student;
	}

	@Deprecated
	public static Homework createHomework(Student student, Module module) {
		Homework homework = new Homework();
		homework.setModules(Arrays.asList(module));
		homework.setStudent(student);
		student.setHomeworks(Arrays.asList(homework));
		return homework;
	}
	
	public static Homework enrichHomeworkWithProgress(Homework homework,
			Topic... topics) {
		return enrichHomeworkWithProgress(homework, Arrays.asList(topics));
	}
	
	@Deprecated
	public static Homework enrichHomeworkWithProgress(Homework homework,
			List<Topic> topics) {
		ArrayList<TopicProgress> progresses = new ArrayList<TopicProgress>();
		homework.setProgresses(progresses);
		int i = 1;
		for (Topic topic : topics) {
			topic.setName("Test topic " + i);
			TopicProgress progress = new TopicProgress(topic, homework);
			progresses.add(progress);
			progress.setStatus("10");
			i++;
		}
		return homework;
	}
	
//	public static List<TopicProgress> enrichTopicProgressesWithId(
//			List<TopicProgress> tp) {
//		int i = 1;
//		for (TopicProgress p : tp) {
//			//p.setId(10 + i);
//			i++;
//		}
//		return tp;
//	}

	public static HomeworkInfo createHomeworkInfo() {
		HomeworkInfo result = new HomeworkInfo();
		result.setId(1);
		result.setAssignedModules("Test Module");
		return result;
	}

	@Deprecated
	public static HomeworkInfo createHomeworkInfoWithDetails() {
		HomeworkInfo result = createHomeworkInfo();
		
		ArrayList<TopicProgressInfo> topicProgress = new ArrayList<TopicProgressInfo>();
		result.setTopicProgress(topicProgress);
		
		TopicProgressInfo info = createTopicProgressInfo(1, "TestTopic1");
		info.setStatus("0");
		topicProgress.add(info);
		
		info = createTopicProgressInfo(2, "TestTopic2");
		info.setStatus("20");
		topicProgress.add(info);
		
		info = createTopicProgressInfo(3, "TestTopic3");
		info.setStatus("100");
		topicProgress.add(info);
		
		info = createTopicProgressInfo(4, "TestTopic4");
		info.setStatus(null);
		topicProgress.add(info);
		
		return result;
	}

	@Deprecated
	public static TopicProgressInfo createTopicProgressInfo(int id, String name) {
		TopicProgressInfo info = new TopicProgressInfo();
		info.setTopicId(id);
		info.setStatus("10");
		info.setTopicName(name);
		return info;
	}
	
	public static TopicDetailedProgressInfo createTopicDetailedProgressInfo(int id, String name) {
		TopicDetailedProgressInfo info = new TopicDetailedProgressInfo();
		info.setTopicId(id);
		info.setStatus("10");
		info.setTopicName(name);
		info.setDescription("Some Topic description");
		return info;
	}
	
	public static ModuleInfo createModuleInfo() {
		ModuleInfo info = new ModuleInfo();
		info.setId(1);
		info.setName("name");
		return info;
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

	public static void createMockExpectationFor(SsgMessages messages) {
		when(messages.topicViewTopicStatusInProgress()).thenReturn(
				"In Progress");
		when(messages.topicViewTopicStatusDone()).thenReturn("Done");
		when(messages.topicViewTopicStatusNosStarted()).thenReturn(
				"Not Started");
	}
	
	@SuppressWarnings("serial")
	public static class TestAction extends BaseAction<TestActionResponse> {
		public TestAction() {
			setActionName("Test/TestAction/" + new Date().toString());
		}
	}

	public static class TestActionResponse implements Response {

	}

	public static class TestActionHandler implements
			ActionHandler<TestActionResponse, TestAction> {

		public TestActionResponse execute(TestAction action)
				throws SsgGuiServiceException {
			return null;
		}

		public Class<TestAction> forClass() {
			return TestAction.class;
		}

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
