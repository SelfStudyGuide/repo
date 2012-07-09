package org.ssg.gui.client.service.sender;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.support.TstDataUtils.TestAction;
import org.ssg.core.support.TstDataUtils.TestActionCallback;
import org.ssg.core.support.TstDataUtils.TestActionResponse;
import org.ssg.gui.client.errordialog.ErrorDialog;
import org.ssg.gui.client.service.SsgGuiServiceException;
import org.ssg.gui.client.service.UnexpectedCommandException;
import org.ssg.gui.client.service.res.SsgLookupMessages;
import org.ssg.gui.client.service.res.SsgMessages;

@RunWith(MockitoJUnitRunner.class)
public class DefaultActionResponseCallbackProcessorTest {
	@Mock
	private TestActionCallback actionCallback;

	@Mock
	private ErrorDialog dialog;

	@Mock
	private SsgMessages messages;

	@Mock
	private SsgLookupMessages ssgLookupMessages;

	private DefaultActionResponseCallbackProcessor processor;

	private TestAction action;

	@Before
	public void setUp() {
		action = new TestAction();
		when(ssgLookupMessages.getString(eq("error_code"))).thenReturn("oups");
		processor = new DefaultActionResponseCallbackProcessor(dialog,
				messages, ssgLookupMessages);
	}

	@Test
	public void verifyThatSuccessfulResponseCallesOnResponseMethodOfActionCallback() {
		TestActionResponse response = new TestActionResponse();

		processor.processResponse(action, actionCallback).onSuccess(response);

		verify(actionCallback).onResponse(same(response));
	}

	@Test
	public void givenUnexpectedCommandExceptionWhenProcessExceptionThenDisplayErrorDialog() {
		// Given
		UnexpectedCommandException ex = new UnexpectedCommandException(
				"something happened", "", action.getActionName());
		when(messages.serviceErrorUnexpected(eq("something happened")))
				.thenReturn("oups");

		// When
		processor.processResponse(action, actionCallback).onFailure(ex);

		// Then
		verify(dialog).show(eq("oups"), eq(action.getActionName()));
	}
	
	@Test
	public void givenUnexpectedCommandExceptionWhenProcessExceptionThenActionCallbackShouldntBeCalled() {
		// Given
		UnexpectedCommandException ex = new UnexpectedCommandException(
				"something happened", "", action.getActionName());
		when(messages.serviceErrorUnexpected(eq("something happened")))
				.thenReturn("oups");

		// When
		processor.processResponse(action, actionCallback).onFailure(ex);

		// Then
		verify(actionCallback, times(0)).onError(same(ex));
	}

	@Test
	public void givenSsgGuiServiceExceptionWhichIsUnhandledWhenProcessingItThenDisplayErrorDialog() {

		SsgGuiServiceException ex = new SsgGuiServiceException("description",
				"error.code");

		// When
		processor.processResponse(action, actionCallback).onFailure(ex);

		// Then
		verify(dialog).show(eq("oups"), eq(action.getActionName()));
	}

	@Test
	public void givenSsgGuiServiceExceptionWhichIsAlreadyHandledWhenProcessingItThenDontDisplayErrorDialog() {
		// Given
		SsgGuiServiceException ex = new SsgGuiServiceException("description",
				"error.code");
		ex.setHandled(true);

		// When
		processor.processResponse(action, actionCallback).onFailure(ex);

		// Then
		verify(dialog, times(0)).show(eq("oups"), eq(action.getActionName()));
	}
	
	@Test
	public void givenSsgGuiServiceExceptionWhenProcessingItThenActionCallbackShouldBeCalled() {
		// When
		SsgGuiServiceException ex = new SsgGuiServiceException("description",
		"error.code");
		
		// When
		processor.processResponse(action, actionCallback).onFailure(ex);
		
		// Then
		verify(actionCallback).onError(same(ex));
	}
}
