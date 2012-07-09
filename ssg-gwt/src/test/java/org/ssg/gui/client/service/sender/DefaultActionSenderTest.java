package org.ssg.gui.client.service.sender;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.ssg.gui.client.action.Response;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.ActionNameProvider;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;
import org.ssg.gui.client.service.sender.DefaultActionResponseCallbackProcessor;
import org.ssg.gui.client.service.sender.DefaultActionSender;

import com.google.gwt.user.client.rpc.AsyncCallback;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActionSenderTest extends AbstractServiceTestCase {

	@Mock
	private TestActionCallback actionCallback;
	
	@Mock
	private ActionNameProvider nameProvider;
	
	@Mock
	private ErrorDialog dialog; 
	
	@Mock 
	private SsgMessages messages;
	
	@Mock
	private SsgLookupMessages ssgLookupMessages;

	private DefaultActionSender sender;

	private DefaultActionResponseCallbackProcessor processor;

	private TestAction action;

	@Before
	public void setUp() {
		action = new TestAction();
		when(ssgLookupMessages.getString(eq("error_code"))).thenReturn("oups");
		
		processor = new DefaultActionResponseCallbackProcessor(dialog,
				messages, ssgLookupMessages);
		sender = new DefaultActionSender(service, nameProvider, processor);
		
	}

	@Test
	public void verifyThatActionIsSentViaSender() {
		sender.send(action, actionCallback);

		TestAction verifyAction = verifyAction(TestAction.class);
		assertThat(verifyAction, is(action));
	}

	@Test
	public void verifyThatResponseIsReturnedViaCallback() {
		sender.send(action, actionCallback);

		AsyncCallback<Response> callback = verifyActionAndResturnCallback(TestAction.class);
		TestActionResponse response = new TestActionResponse();
		callback.onSuccess(response);

		verify(actionCallback).onResponse(same(response));
	}

	@Test
	public void verifyThatExceptionIsReturnedViaCallback() {
		sender.send(action, actionCallback);

		AsyncCallback<Response> callback = verifyActionAndResturnCallback(TestAction.class);
		SsgGuiServiceException ex = new SsgGuiServiceException("");
		callback.onFailure(ex);

		verify(actionCallback).onError(same(ex));
	}

	@Test
	public void verifyThatNameOfActionIsPopulated() {
		// Given
		when(nameProvider.getActionName(eq(action))).thenReturn("action name");
		
		// When
		sender.send(action, actionCallback);
		
		//Then
		TestAction verifyAction = verifyAction(TestAction.class);
		assertThat(verifyAction.getActionName(), equalTo("action name"));
	}

	@Test
	public void verifyThatResponseObjectIsPopulatedToHandler() {
		TestActionCallbackWithAdapter actionCallback = Mockito.mock(TestActionCallbackWithAdapter.class);
		
		sender.send(action, actionCallback);
		
		verify(actionCallback).setAction(same(action));
	}
	
	
}
