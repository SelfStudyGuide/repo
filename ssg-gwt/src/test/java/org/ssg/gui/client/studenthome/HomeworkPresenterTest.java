package org.ssg.gui.client.studenthome;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.dto.HomeworkInfo;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.client.studenthome.action.GetHomeworks;
import org.ssg.gui.client.studenthome.action.GetHomeworksResponse;
import org.ssg.gui.client.studenthome.event.RefreshStudentHomeworksEvent;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter;
import org.ssg.gui.client.studenthome.presenter.HomeworkPresenter.Display;
import org.ssg.gui.shared.Action;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;

@RunWith(MockitoJUnitRunner.class)
public class HomeworkPresenterTest {

	@Mock
	private Display view;

	@Mock
	private HasClickHandlers refreshButton;

	@Mock
	private HasData<HomeworkInfo> grid;

	@Mock
	private StudentControlServiceAsync service;

	private HandlerManager handlerManager = new HandlerManager(null);

	@InjectMocks
	private HomeworkPresenter presenter = new HomeworkPresenter(view, service,
			handlerManager);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(view.getRefreshButton()).thenReturn(refreshButton);
		when(view.getGrid()).thenReturn(grid);
		presenter.bind();
	}

	@Test
	public void verifyThatActionGetHomeworkIsExecutedWhenRefreshHomeworkEvent() {
		handlerManager.fireEvent(new RefreshStudentHomeworksEvent());

		GetHomeworks action = verifyAction(GetHomeworks.class);

		assertThat(action.getStudentId(), is(1));
	}

	@Test
	public void verifyThatAfterClickRefreshButtonActionGetHomeworkIsExecuted() {
		ArgumentCaptor<ClickHandler> click = ArgumentCaptor
				.forClass(ClickHandler.class);
		verify(refreshButton).addClickHandler(click.capture());
		ClickHandler refreshHandler = click.getValue();

		refreshHandler.onClick(null);

		GetHomeworks action = verifyAction(GetHomeworks.class);

		assertThat(action.getStudentId(), is(1));

	}

	@Test
	public void verifyThatViewIsUpdatedWithAListOfHomeworksFromServer() {
		handlerManager.fireEvent(new RefreshStudentHomeworksEvent());
		
		ArgumentCaptor<AsyncCallback> acCaptor = ArgumentCaptor
				.forClass(AsyncCallback.class);		
		verify(service).execute(isA(GetHomeworks.class), acCaptor.capture());		
		AsyncCallback ac = acCaptor.getValue();		
		
		ArrayList<HomeworkInfo> data = new ArrayList<HomeworkInfo>();
		data.add(new HomeworkInfo());
		data.add(new HomeworkInfo());
		
		ac.onSuccess(new GetHomeworksResponse(data));
		
		verify(grid).setRowData(0, data);
		verify(grid).setRowCount(2);
	}

	@SuppressWarnings("unchecked")
	private <A extends Action<?>> A verifyAction(Class<A> actionClass) {
		ArgumentCaptor<? extends Action<?>> argument = ArgumentCaptor
				.forClass(actionClass);
		verify(service).execute(argument.capture(), isA(AsyncCallback.class));
		return (A) argument.getValue();
	}
}
