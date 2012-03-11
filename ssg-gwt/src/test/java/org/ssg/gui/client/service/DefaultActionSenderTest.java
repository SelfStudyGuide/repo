package org.ssg.gui.client.service;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.support.TstDataUtils.TestAction;
import org.ssg.core.support.TstDataUtils.TestActionCallback;
import org.ssg.core.support.TstDataUtils.TestActionCallbackWithAdapter;
import org.ssg.core.support.TstDataUtils.TestActionResponse;
import org.ssg.gui.client.AbstractServiceTestCase;
import org.ssg.gui.shared.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActionSenderTest extends AbstractServiceTestCase {

	@Mock
	private TestActionCallback actionCallback;
	
	@Mock
	private ActionNameProvider nameProvider;

	private DefaultActionSender sender;

	@Before
	public void setUp() {
		// There are limitation in Mockito runner when one of Mocks in super
		// class
		sender = new DefaultActionSender(service, nameProvider);
	}

	@Test
	public void verifyThatActionIsSentViaSender() {
		TestAction action = new TestAction();
		sender.send(action, actionCallback);

		TestAction verifyAction = verifyAction(TestAction.class);
		assertThat(verifyAction, is(action));
	}

	@Test
	public void verifyThatResponseIsReturnedViaCallback() {
		TestAction action = new TestAction();
		sender.send(action, actionCallback);

		AsyncCallback<Response> callback = verifyActionAndResturnCallback(TestAction.class);
		TestActionResponse response = new TestActionResponse();
		callback.onSuccess(response);

		verify(actionCallback).onResponse(same(response));
	}

	@Test
	public void verifyThatExceptionIsReturnedViaCallback() {
		TestAction action = new TestAction();
		sender.send(action, actionCallback);

		AsyncCallback<Response> callback = verifyActionAndResturnCallback(TestAction.class);
		SsgGuiServiceException ex = new SsgGuiServiceException("");
		callback.onFailure(ex);

		verify(actionCallback).onError(same(ex));
	}

	@Test
	public void verifyThatNameOfActionIsPopulated() {
		TestAction action = new TestAction();
		when(nameProvider.getActionName(eq(action))).thenReturn("action name");
		
		sender.send(action, actionCallback);
		TestAction verifyAction = verifyAction(TestAction.class);
		
		assertThat(verifyAction.getActionName(), equalTo("action name"));
	}

	@Test
	public void verifyThatResponseObjectIsPopulatedToHandler() {
		TestAction action = new TestAction();
		
		TestActionCallbackWithAdapter actionCallback = Mockito.mock(TestActionCallbackWithAdapter.class);
		
		sender.send(action, actionCallback);
		
		verify(actionCallback).setAction(same(action));
	}
	
}
