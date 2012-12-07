package org.ssg.core.support;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.ssg.core.domain.ApplicationUser;
import org.ssg.core.domain.ApplicationUserImpl;
import org.ssg.core.domain.Homework;
import org.ssg.core.domain.Module;
import org.ssg.core.domain.Student;
import org.ssg.core.domain.Task;
import org.ssg.core.domain.Topic;
import org.ssg.core.domain.TopicProgress;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkDetailedInfo;
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
import org.ssg.gui.server.command.ActionHandler;
import org.ssg.gui.server.security.Authorization;
import org.ssg.gui.server.security.CommandPreAuthorize;
import org.ssg.gui.server.security.SsgSecurityException;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class TstDataUtils {

	public static Module createModule() {
		return TstDataBuilder.moduleBuilder().name("name").description("desc").build();
	}

	public static Module createModule(String name, int id) {
		return TstDataBuilder.moduleBuilder().name(name).id(id).build();
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
		return TstDataBuilder.moduleBuilder().name("unique module name " + System.currentTimeMillis())
		        .description("desc").build();
	}

	public static Topic createTopicWithUniqueName(Module module) {
		return TstDataBuilder.topicBuilder().module(module).unigueName("unique topic name").build();
	}

	@Deprecated
	public static Topic createTopic(String name, int id) {
		Topic topic = createTopicWithUniqueName(null);
		topic.setName(name);
		topic.setId(id);
		topic.setDescription("Description of topic");
		return topic;
	}

	public static Task createTask(TaskType type) {
		return TstDataBuilder.taskBuilder().name(type.toString() + " task").type(type)
		        .description(type.toString() + " task descr").execrisesCount(2).build();
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

	public static Homework createHomework(Student student, Module module) {
		return TstDataBuilder.homeworkBuilder().module(module).student(student).build();
	}

	public static Homework enrichHomeworkWithProgress(Homework homework, Topic... topics) {
		return enrichHomeworkWithProgress(homework, Arrays.asList(topics));
	}

	@Deprecated
	public static Homework enrichHomeworkWithProgress(Homework homework, List<Topic> topics) {
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

	public static HomeworkInfo createHomeworkInfo() {
		HomeworkInfo result = new HomeworkInfo();
		result.setId(1);
		result.setAssignedModules("Test Module");
		return result;
	}

	@Deprecated
	public static HomeworkDetailedInfo createHomeworkInfoWithDetails() {
		HomeworkDetailedInfo result = new HomeworkDetailedInfo();
		result.setId(1);
		result.setAssignedModules("Test Module");

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

	public static ApplicationUserInfo createStudentUserInfo(String name, int studentId) {
		ApplicationUserInfo info = new ApplicationUserInfo();
		info.setUsername(name);
		info.setStudentId(studentId);
		return info;
	}

	public static ApplicationUser createAppUserDetails(String username, int studentId) {
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("student"));
		User user = new User(username, "", roles);
		ApplicationUser appUser = new ApplicationUserImpl(user, studentId);
		return appUser;
	}

	// ======================= TestAction =============================

	@SuppressWarnings("serial")
	public static class TestAction extends BaseAction<TestActionResponse> {

		public String param;

		public TestAction(String param) {
			this.param = param;
		}

		public TestAction() {
			setActionName("Test/TestAction/" + new Date().toString());
		}
	}

	public static class TestActionResponse implements Response {
		public String response;

		public TestActionResponse(String response) {
			this.response = response;
		}
	}

	public static class TestActionHandler implements ActionHandler<TestActionResponse, TestAction>,
	        CommandPreAuthorize<TestAction> {

		@Autowired
		private Authorization authorization;

		public TestActionResponse execute(TestAction action) throws SsgGuiServiceException {
			return new TestActionResponse(action.param);
		}

		public Class<TestAction> forClass() {
			return TestAction.class;
		}

		public void preAuthorize(TestAction action) throws SsgSecurityException {
			authorization.student();
		}

	}

	public static class TestActionCallback implements ActionResponseCallback<TestActionResponse> {

		public void onResponse(TestActionResponse response) {
		}

		public void onError(SsgGuiServiceException exception) {
		}
	}

	public static class TestActionCallbackWithAdapter extends ActionCallbackAdapter<TestActionResponse> {

		public void onResponse(TestActionResponse response) {
		}
	}

	// ======================= TestApplicationEvent =====================

	public static class TestApplicationEvent extends GwtEvent<TestApplicationEventHandler> {

		public static final com.google.gwt.event.shared.GwtEvent.Type<TestApplicationEventHandler> TYPE = new Type<TstDataUtils.TestApplicationEventHandler>();

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<TestApplicationEventHandler> getAssociatedType() {
			return TYPE;
		}

		@Override
		protected void dispatch(TestApplicationEventHandler handler) {
			handler.process();
		}

	}

	public interface TestApplicationEventHandler extends EventHandler {
		void process();
	}
}
