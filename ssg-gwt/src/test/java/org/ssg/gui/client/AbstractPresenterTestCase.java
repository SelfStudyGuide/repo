package org.ssg.gui.client;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.ssg.gui.client.service.StudentControlServiceAsync;
import org.ssg.gui.shared.Action;
import org.ssg.gui.shared.Response;
import org.ssg.gui.shared.WindowLocation;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractPresenterTestCase {

	@Mock
	protected StudentControlServiceAsync service;
	@Mock
	protected WindowLocation windowLocation;
	protected HandlerManager handlerManager = new HandlerManager(null);

	protected ClickHandler verifyAndCaptureClickHnd(HasClickHandlers btn) {
		ArgumentCaptor<ClickHandler> click = ArgumentCaptor
				.forClass(ClickHandler.class);
		verify(btn).addClickHandler(click.capture());
		return click.getValue();
	}

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

	protected AssertEventHandler assertAppEvent(GwtEvent.Type type, EventHandler handler) {
		AssertEventHandler assertEventHandler = new AssertEventHandler(handler);
		EventHandler proxy = (EventHandler) Proxy.newProxyInstance(handler
				.getClass().getClassLoader(), new Class[] { handler.getClass()
				.getInterfaces()[0] }, assertEventHandler);
	
		handlerManager.addHandler(type, proxy);
	
		return assertEventHandler;
	}

	protected static class AssertEventHandler implements InvocationHandler {
		
		private boolean invoked;
		private final EventHandler handler;
	
		public AssertEventHandler(EventHandler handler) {
			this.handler = handler;
		}
	
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			invoked = true;
			return method.invoke(handler, args);
		}
	
		public void assertInvoked() {
			assertThat("Event expected to be fired", invoked, is(true));
		}
	}

}