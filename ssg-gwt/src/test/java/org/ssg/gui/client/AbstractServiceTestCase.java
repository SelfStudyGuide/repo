package org.ssg.gui.client;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class AbstractServiceTestCase {

	@Mock
	protected StudentControlServiceAsync service;

	
	@SuppressWarnings("unchecked")
	protected <A extends Action<?>> A verifyAction(Class<A> actionClass) {
		ArgumentCaptor<? extends Action<?>> argument = ArgumentCaptor
				.forClass(actionClass);
		verify(service).execute(argument.capture(), any(AsyncCallback.class));
		A action = (A) argument.getValue();
		assertThat(action, notNullValue());
		return action;
	}

	@SuppressWarnings("unchecked")
	protected AsyncCallback<Response> verifyActionAndResturnCallback(Class<? extends Action> actionClass) {
		ArgumentCaptor<AsyncCallback> acCaptor = ArgumentCaptor
				.forClass(AsyncCallback.class);
		verify(service).execute(isA(actionClass), acCaptor.capture());
		AsyncCallback<Response> ac = acCaptor.getValue();
	
		return ac;
	}

}