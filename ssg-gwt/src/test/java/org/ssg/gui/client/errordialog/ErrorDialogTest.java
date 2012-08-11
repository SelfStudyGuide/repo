package org.ssg.gui.client.errordialog;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ssg.core.support.TstDataUtils;
import org.ssg.core.support.TstDataUtils.TestAction;
import org.ssg.gui.client.AbstractPresenterTestCase;
import org.ssg.gui.client.errordialog.ErrorDialog.Display;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;

@RunWith(MockitoJUnitRunner.class)
public class ErrorDialogTest extends AbstractPresenterTestCase {
	private ErrorDialog dialog;

	@Mock
	private Display view;

	@Mock
	private HasText actionInfoBox;

	@Mock
	private HasHTML messageBox;

	@Mock
	private HasClickHandlers closeBtn;

	@Mock
	private ErrorDialogMessages messages;

	@Before
	public void setUp() {
		when(view.getActionInfoBox()).thenReturn(actionInfoBox);
		when(view.getMessageBox()).thenReturn(messageBox);
		when(view.getCloseButton()).thenReturn(closeBtn);
		dialog = new ErrorDialog(view, messages);
	}

	@Test
	public void verifyThatGwtDialogIsShowedWhenDialogIsCalled() {
		TestAction action = new TstDataUtils.TestAction();
		action.setActionName("Test/TestAction");

		dialog.show("Error message", action);

		verify(view).show();
	}

	@Test
	public void verifyThatActionInfoIsUpdatedWhenDialogIsCalled() {
		TestAction action = new TstDataUtils.TestAction();
		action.setActionName("Test/TestAction");
		when(messages.getActionInfo(eq("Test/TestAction"))).thenReturn("Action Name: Test/TestAction");

		dialog.show("Error message", action);

		verify(actionInfoBox).setText(eq("Action Name: Test/TestAction"));
	}

	@Test
	public void verifyThatMessageInfoIsUpdatedWhenDialogIsCalled() {
		TestAction action = new TstDataUtils.TestAction();
		action.setActionName("Test/TestAction");
		when(messages.getActionInfo(eq("Test/TestAction"))).thenReturn("Action Name: Test/TestAction");

		dialog.show("Error message", action);

		verify(messageBox).setHTML(eq("Error message"));
	}

	@Test
	public void verifyThatWindowIsClosedWhenClickCloseButton() {
		ClickHandler handler = verifyAndCaptureClickHnd(closeBtn);
		handler.onClick(null);

		verify(view).hide();
	}
}
