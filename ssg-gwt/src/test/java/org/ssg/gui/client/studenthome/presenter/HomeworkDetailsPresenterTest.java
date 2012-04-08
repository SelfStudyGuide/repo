package org.ssg.gui.client.studenthome.presenter;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.DefaultActionSender;
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

	private DefaultActionSender actionSender;

	@Mock
	private HasText moduleName;

	@Before
	public void setUp() {
		when(view.getModuleName()).thenReturn(moduleName);
		
		actionSender = new DefaultActionSender(service,
				new DefaultActionNameProvider(), errorDialog, messages);

		homeworkDetailsPresenter = new HomeworkDetailsPresenter(view, messages,
				windowLocation, actionSender, handlerManager);
		homeworkDetailsPresenter.bind();

		UserInfoHolder.setAppInfo(TstDataUtils.createStudentUserInfo("John",
				1234));
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
	public void verifyThatIfHwDetailsIsReceivedThenModulesAreUpdated() {
		fireHomeworkSelectEvent();		
		mockExternalServiceCalls();
		processResponse();

		verify(view).addTopic(eq("TestTopic1 - Not strarted"),
				eq("http://localhost/ssg/Topic.html?id=1"));
		verify(view).addTopic(eq("TestTopic2 - In progress"),
				eq("http://localhost/ssg/Topic.html?id=2"));
		verify(view).addTopic(eq("TestTopic3 - Done"),
				eq("http://localhost/ssg/Topic.html?id=3"));
	}

	private void mockExternalServiceCalls() {
		when(messages.homeworkDetailsDone(eq("TestTopic3"))).thenReturn(
				"TestTopic3 - Done");
		when(messages.homeworkDetailsInProgress(eq("TestTopic2"))).thenReturn(
				"TestTopic2 - In progress");
		when(messages.homeworkDetailsNotStarted(eq("TestTopic1"))).thenReturn(
				"TestTopic1 - Not strarted");
		when(windowLocation.getUrl(eq("Topic.html?id=3"))).thenReturn(
				"http://localhost/ssg/Topic.html?id=3");
		when(windowLocation.getUrl(eq("Topic.html?id=2"))).thenReturn(
				"http://localhost/ssg/Topic.html?id=2");
		when(windowLocation.getUrl(eq("Topic.html?id=1"))).thenReturn(
				"http://localhost/ssg/Topic.html?id=1");
	}

	@Test
	public void verifyThatIfHwDetailsIsReceivedThenModuleNameIsUpdated() {
		fireHomeworkSelectEvent();
		processResponse();

		verify(moduleName).setText("Test Module");
	}

	private void processResponse() {
		AsyncCallback<Response> callback = verifyActionAndResturnCallback(GetHomeworkDetails.class);
		HomeworkInfo received = TstDataUtils.createHomeworkInfoWithDetails();
		received.setId(567);
		callback.onSuccess(new GetHomeworkDetailsResponse(received));
	}
	
	
}
