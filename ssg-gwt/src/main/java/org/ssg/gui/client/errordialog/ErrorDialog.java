package org.ssg.gui.client.errordialog;


import org.ssg.gui.client.action.Action;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;

public class ErrorDialog {

	private Display view;
	private ErrorDialogMessages messages;

	public interface Display {
		HasClickHandlers getCloseButton();

		HasHTML getMessageBox();

		HasText getActionInfoBox();

		void show();

		void hide();
	}

	public ErrorDialog(Display view, ErrorDialogMessages messages) {
		this.view = view;
		this.messages = messages;

		view.getCloseButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ErrorDialog.this.view.hide();
			}
		});
	}

	public void show(String message, Action<?> action) {
		view.getActionInfoBox().setText(
				messages.getActionInfo(action.getActionName()));
		view.getMessageBox().setHTML(message);
		view.show();
	}

}
