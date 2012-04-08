package org.ssg.gui.client.studenthome;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.ApplicationUserInfo;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.core.support.TstDataUtils;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.service.ActionSender;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.DefaultActionSender;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.client.studenthome.event.HomeworkSelectedEvent;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEvent;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter.Display;
import org.ssg.gui.client.userinfo.event.ShareUserInfoEvent;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;
import com.google.gwt.view.client.SingleSelectionModel;

@RunWith(MockitoJUnitRunner.class)
public class HomeworkPresenterTest extends AbstractPresenterTestCase {

	@Mock
	private Display view;

	@Mock
	private HasClickHandlers refreshButton;

	@Mock
	private HasData<HomeworkInfo> grid;
	
	@Mock
	private SingleSelectionModel<HomeworkInfo> homeworkSelection;

	private HomeworkPresenter presenter;
	
	private ActionSender actionSender;
	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		actionSender = new DefaultActionSender(service,
				new DefaultActionNameProvider(), errorDialog, messages);
		presenter = new HomeworkPresenter(view, actionSender, handlerManager,
				errorDialog);
		when(view.getRefreshButton()).thenReturn(refreshButton);
		when(view.getGrid()).thenReturn(grid);
		when(view.getSelectionModel()).thenReturn(homeworkSelection);
		presenter.bind();
	}
	
	@Test
	public void verifyThatAfterUserInfoEventRequestToRefreshHomeWorksIsExecuted() {
		assertThat(service, notNullValue());
		
		ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 2);
		
		handlerManager.fireEvent(new ShareUserInfoEvent(userInfo));
		GetHomeworks action = verifyAction(GetHomeworks.class);

		assertThat(action.getStudentId(), is(2));
		
	}

	@Test
	@Ignore
	public void verifyThatActionGetHomeworkIsExecutedWhenRefreshHomeworkEvent() {
		handlerManager.fireEvent(new RefreshStudentHomeworksEvent());

		GetHomeworks action = verifyAction(GetHomeworks.class);

		assertThat(action.getStudentId(), is(1));
	}

	@Test
	public void verifyThatAfterClickRefreshButtonActionGetHomeworkIsExecuted() {
		
		presenter.setUserInfo(TstDataUtils.createStudentUserInfo("Foo", 3));

		ClickHandler refreshHandler = verifyAndCaptureClickHnd(refreshButton);
		
		refreshHandler.onClick(null);

		GetHomeworks action = verifyAction(GetHomeworks.class);

		assertThat(action.getStudentId(), is(3));

	}

	@Test
	public void verifyThatViewIsUpdatedWithAListOfHomeworksFromServer() {
		ApplicationUserInfo userInfo = TstDataUtils.createStudentUserInfo("John Dou", 2);
		handlerManager.fireEvent(new ShareUserInfoEvent(userInfo));
		
		AsyncCallback ac = verifyActionAndResturnCallback(GetHomeworks.class);
		
		ArrayList<HomeworkInfo> data = new ArrayList<HomeworkInfo>();
		data.add(new HomeworkInfo());
		data.add(new HomeworkInfo());
		
		ac.onSuccess(new GetHomeworksResponse(data));
		
		verify(grid).setRowData(0, data);
		verify(grid).setRowCount(2);
	}
	
	@Test
	public void verifyThatHwSelectionEventIsFiredIfRowIsSelected() {
		final HomeworkInfo homeworkInfo = TstDataUtils.createHomeworkInfo();
		Handler selectionHnd = verifyAndCaptureSelectionHnd(homeworkSelection);
		AssertEventHandler assertAppEvent = verifyAppEvent(
				HomeworkSelectedEvent.TYPE,
				new HomeworkSelectedEvent.Handler() {
					public void onHomeworkSelection(HomeworkInfo selected) {
						assertThat(selected.getId(), is(homeworkInfo.getId()));
					}
				});
		when(homeworkSelection.getSelectedObject()).thenReturn(homeworkInfo);

		selectionHnd.onSelectionChange(null);

		assertAppEvent.assertInvoked();
	}
	
}
