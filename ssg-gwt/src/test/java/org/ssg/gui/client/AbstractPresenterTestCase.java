package org.ssg.gui.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.DefaultActionNameProvider;
import org.ssg.gui.client.service.WindowLocation;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.res.SsgMessagesMock;
import org.ssg.gui.client.service.sender.DefaultActionResponseCallbackProcessor;
import org.ssg.gui.client.service.sender.DefaultActionSender;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;

/**
 * This class provide base facilities for test cases on presenter classes.
 */
public abstract class AbstractPresenterTestCase extends AbstractServiceTestCase {

	protected HandlerManager handlerManager = new HandlerManager(null);

	@Mock
	protected WindowLocation windowLocation;

	@Mock
	protected ErrorDialog errorDialog;

	protected SsgMessages ssgMessages;

	@Mock
	protected SsgLookupMessages ssgLookupMessages;

	protected DefaultActionSender actionSender;

	protected DefaultActionResponseCallbackProcessor processor;

	@Before
	public void mockMessages() {
		ssgMessages = SsgMessagesMock.mockMsg(SsgMessages.class);

		when(windowLocation.getUrl(Mockito.anyString())).thenAnswer(new Answer<String>() {

			public String answer(InvocationOnMock invocation) throws Throwable {
				return "http://localhost/ssg/" + invocation.getArguments()[0];
			}
		});

		processor = new DefaultActionResponseCallbackProcessor(errorDialog, ssgMessages, ssgLookupMessages);
		actionSender = new DefaultActionSender(service, new DefaultActionNameProvider(), processor);

	}

	protected ClickHandler verifyAndCaptureClickHnd(HasClickHandlers btn) {
		ArgumentCaptor<ClickHandler> click = ArgumentCaptor.forClass(ClickHandler.class);
		verify(btn).addClickHandler(click.capture());
		return click.getValue();
	}

	protected SelectionChangeEvent.Handler verifyAndCaptureSelectionHnd(HasSelectionChangedHandlers select) {
		ArgumentCaptor<SelectionChangeEvent.Handler> click = ArgumentCaptor
		        .forClass(SelectionChangeEvent.Handler.class);
		verify(select).addSelectionChangeHandler(click.capture());
		return click.getValue();
	}

	protected <H extends EventHandler> AssertEventHandler verifyAppEvent(GwtEvent.Type<H> type, H handler) {
		AssertEventHandler assertEventHandler = new AssertEventHandler(handler);
		@SuppressWarnings("unchecked")
		H proxy = (H) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] { handler.getClass()
		        .getInterfaces()[0] }, assertEventHandler);

		handlerManager.addHandler(type, proxy);

		return assertEventHandler;
	}

	protected <H extends EventHandler> AssertEventHandler verifyAppEvent(GwtEvent.Type<H> type, Class<H> handlerClass) {
		AssertEventHandler assertEventHandler = new AssertEventHandler(null);
		@SuppressWarnings("unchecked")
		H proxy = (H) Proxy.newProxyInstance(handlerClass.getClassLoader(), new Class[] { handlerClass },
		        assertEventHandler);

		handlerManager.addHandler(type, proxy);

		return assertEventHandler;
	}

	protected static class AssertEventHandler implements InvocationHandler {

		private boolean invoked;
		private final EventHandler handler;
		private Method lastInvokedMethod;
		private Object[] lastArguments;

		public AssertEventHandler(EventHandler handler) {
			this.handler = handler;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			invoked = true;
			lastInvokedMethod = method;
			lastArguments = args;
			return handler == null ? null : method.invoke(handler, args);
		}

		public void assertInvoked() {
			assertThat("Event expected to be fired", invoked, is(true));
		}

		public void assertMethodInvoked(String name) {
			Assert.assertThat("Handler method", lastMethodName(), equalTo(name));
		}

		public String lastMethodName() {
			return lastInvokedMethod == null ? null : lastInvokedMethod.getName();
		}

		public <T> T lastArgument(int idx, Class<T> argClass) {
			assertInvoked();
			return argClass.cast(lastArguments[idx]);
		}
	}

}