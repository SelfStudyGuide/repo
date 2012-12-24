package org.ssg.gui.client.task.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class NotFoundExerciseView extends Composite implements ExerciseView {

	private HasText messageLabel;

	public NotFoundExerciseView(){
		messageLabel = new Label("Execrise view is under construction.");

		initWidget((Widget) messageLabel);
	}

	public void go(ForIsWidget panel) {
		panel.add(this);
	}

}
