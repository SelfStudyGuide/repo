package org.ssg.gui.client;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.studenthome.StudentHomeworkMessages;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;

public abstract class AbstractPresenterTestCase extends AbstractServiceTestCase {

	@Mock
	protected WindowLocation windowLocation;
	protected HandlerManager handlerManager = new HandlerManager(null);
	
	@Mock
	protected ErrorDialog errorDialog; 
	
	@Mock 
	protected StudentHomeworkMessages messages;

	protected ClickHandler verifyAndCaptureClickHnd(HasClickHandlers btn) {
		ArgumentCaptor<ClickHandler> click = ArgumentCaptor
				.forClass(ClickHandler.class);
		verify(btn).addClickHandler(click.capture());
		return click.getValue();
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