package org.ssg.gui.client.errordialog.view;

import org.ssg.gui.client.errordialog.ErrorDialog.Display;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;

public class ErrorDialogView implements Display {
	private static final long serialVersionUID = 1L;

	@UiTemplate("ErrorDialogView.ui.xml")
	interface MyUiBinder extends UiBinder<DialogBox, ErrorDialogView> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	public Button closeBtn;

	@UiField
	public Label actionInfoLbl;

	@UiField
	public HTML messageTextLbl;

	@UiField
	public DialogBox dialogBox;

	public ErrorDialogView() {
		uiBinder.createAndBindUi(this);
	}

	public HasClickHandlers getCloseButton() {
		return closeBtn;
	}

	public HasHTML getMessageBox() {
		return messageTextLbl;
	}

	public HasText getActionInfoBox() {
		return actionInfoLbl;
	}

	public void show() {
		dialogBox.center();
	}

	public void hide() {
		dialogBox.hide();
	}

}
