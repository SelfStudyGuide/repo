package org.ssg.gui.client.errordialog;

import com.google.gwt.i18n.client.Messages;

public interface ErrorDialogMessages extends Messages {
	@Key("errorDialog.actionInfo")
	String getActionInfo(String actionName);

	@Key("errorDialog.closeButton")
	String getCloseButtonText();

	@Key("errorDialog.caption")
	String getCaptionText();

}
