package org.ssg.gui.client.studenthome.presenter;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.HomeworkDetailedInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.UserInfoHolder;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetails;
import org.ssg.gui.client.studenthome.action.GetHomeworkDetailsResponse;
import org.ssg.gui.client.studenthome.event.HomeworkSelectedEvent;
import org.ssg.gui.client.studenthome.presenter.HomeworkDetailsPresenter.Display;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class HomeworkDetailsPresenterTest extends AbstractPresenterTestCase {

	private HomeworkDetailsPresenter homeworkDetailsPresenter;

	@Mock
	private Display view;

	@Mock
	private HasText moduleName;

	@Mock
	private HasText assignedDate;

	@Mock
	private HasText completeDate;

	@Mock
	private HasText teacher;

	@Before
	public void setUp() {
		when(view.getModuleName()).thenReturn(moduleName);
		when(view.getAssignedDate()).thenReturn(assignedDate);
		when(view.getCompleteDate()).thenReturn(completeDate);
		when(view.getTeacherName()).thenReturn(teacher);

		homeworkDetailsPresenter = new HomeworkDetailsPresenter(view, ssgMessages, windowLocation, actionSender,
		        handlerManager);
		homeworkDetailsPresenter.bind();

		UserInfoHolder.setAppInfo(TstDataUtils.createStudentUserInfo("John", 1234));
	}

	@Test
	public void verifyThatAfterHwSelectEventViewIsHidden() {
		fireHomeworkSelectEvent();
		verify(view).hide();
	}

	@Test
	public void verifyThatAfterHwSelectEventGetHwRequestIsSend() {
		fireHomeworkSelectEvent();

		GetHomeworkDetails action = verifyAction(GetHomeworkDetails.class);
		assertThat(action.getStudentId(), is(1234));
		assertThat(action.getHomeworkId(), is(567));
	}

	private void fireHomeworkSelectEvent() {
		HomeworkInfo selected = TstDataUtils.createHomeworkInfo();
		selected.setId(567);
		handlerManager.fireEvent(new HomeworkSelectedEvent(selected));
	}

	@Test
	public void verifyThatIfHwDetailsIsReceivedThenViewIsShown() {
		fireHomeworkSelectEvent();
		mockExternalServiceCalls();
		processResponse();

		verify(view).show();
	}

	@Test
	public void verifyThatIfHwDetailsIsReceivedThenModulesAreUpdated() {
		fireHomeworkSelectEvent();
		mockExternalServiceCalls();
		processResponse();

		verify(view).addTopic(eq("TestTopic1 - Not strarted"), eq("http://localhost/ssg/Topic.html?hid=567&tid=1"));
		verify(view).addTopic(eq("TestTopic2 - In progress"), eq("http://localhost/ssg/Topic.html?hid=567&tid=2"));
		verify(view).addTopic(eq("TestTopic3 - Done"), eq("http://localhost/ssg/Topic.html?hid=567&tid=3"));
		verify(view).addTopic(eq("TestTopic4 - Not strarted"), eq("http://localhost/ssg/Topic.html?hid=567&tid=4"));
	}

	private void mockExternalServiceCalls() {
		when(ssgMessages.homeworkDetailsDone(eq("TestTopic3"))).thenReturn("TestTopic3 - Done");
		when(ssgMessages.homeworkDetailsInProgress(eq("TestTopic2"))).thenReturn("TestTopic2 - In progress");
		when(ssgMessages.homeworkDetailsNotStarted(eq("TestTopic1"))).thenReturn("TestTopic1 - Not strarted");
		when(ssgMessages.homeworkDetailsNotStarted(eq("TestTopic4"))).thenReturn("TestTopic4 - Not strarted");
	}

	@Test
	public void verifyThatIfHwDetailsIsReceivedThenModuleNameIsUpdated() {
		fireHomeworkSelectEvent();
		processResponse();

		verify(moduleName).setText("Test Module");
	}

	@Test
	public void verifyThatIfDelectEventThenDetailsViewShouldBeHidden() {
		fireDeselectedEvent();

		verify(view).hide();

	}

	private void fireDeselectedEvent() {
		handlerManager.fireEvent(new HomeworkSelectedEvent(null));
	}

	private void processResponse() {
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetHomeworkDetails.class);
		HomeworkDetailedInfo received = TstDataUtils.createHomeworkInfoWithDetails();
		received.setId(567);
		callback.onSuccess(new GetHomeworkDetailsResponse(received));
	}

}
