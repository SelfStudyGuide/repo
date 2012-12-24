package org.ssg.gui.client.task;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.util.StopWatch.TaskInfo;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.ExerciseInfo;
import org.ssg.core.dto.ExerciseType;
import org.ssg.core.dto.TaskType;
import org.ssg.core.dto.TopicTaskDetailedInfo;
import org.ssg.core.support.TstDataBuilder;
import org.ssg.core.support.TstDataUtils;
import org.ssg.core.support.databuilder.ExerciseInfoBuilder;
import org.ssg.core.support.databuilder.TopicTaskDetailedInfoBuilder;
import org.ssg.core.support.databuilder.TopicTaskInfoBuilder;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.UrlQueryString;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.task.action.GetExerciseInfo;
import org.ssg.gui.client.task.action.GetExerciseInfoResponse;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;
import org.ssg.gui.client.userinfo.action.GetUserInfo;
import org.ssg.gui.client.userinfo.action.GetUserInfoResponse;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.assertions.GwtAssertions;

// TaskEntryPoint
@GwtModule("org.ssg.gui.client.Task")
public class TaskEntryPointTest extends GwtTestWithMockito {

	@Mock
	protected StudentControlServiceAsync service;

	@Before
	public void setUp() {
		UrlQueryString.clear();
		UrlQueryString.add("hid", "10");
		UrlQueryString.add("taid", "20");

		setupTestData();
	}

	private void setupTestData() {
		final ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 1);
		doSuccessCallback(new GetUserInfoResponse(userInfo)).when(service).execute(Mockito.isA(GetUserInfo.class),
		        Mockito.any(AsyncCallback.class));

		TopicTaskDetailedInfo topicTaskDetailedInfo = TopicTaskDetailedInfoBuilder.topicTaskDetailedInfoBuilder()
		        .id(20).type(TaskType.TEXT).exerciseIds(11, 12, 13).build();
		
		doSuccessCallback(new GetTaskInfoResponse(topicTaskDetailedInfo)).when(service).execute(
		        Mockito.isA(GetTaskInfo.class), Mockito.any(AsyncCallback.class));
		
		ExerciseInfo exerciseInfo = ExerciseInfoBuilder.exerciseInfoBuilder().id(11).exerciseType(ExerciseType.GENERIC).homeworkId(10).build();
		
		doSuccessCallback(new GetExerciseInfoResponse(exerciseInfo)).when(service).execute(Mockito.isA(GetExerciseInfo.class), Mockito.any(AsyncCallback.class));
	}

	@Test
	public void testOpenTask() {
		TaskEntryPoint entryPoint = new TaskEntryPoint();

		entryPoint.onModuleLoad();

		GwtAssertions.assertThat(entryPoint.getUserInfoView().getUsernameLabel().getText()).isEqualTo("John Dou");
		GwtAssertions.assertThat(entryPoint.getTaskControllerView().getTaskLabel().getText()).isEqualTo("TEXT Task");
		GwtAssertions.assertThat(entryPoint.getTaskControllerView().getExercisePanel().iterator().next()).htmlContains("Execrise view is under construction.");
	}

}
