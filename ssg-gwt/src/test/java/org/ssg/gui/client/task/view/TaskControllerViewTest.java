package org.ssg.gui.client.task.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.assertions.GwtAssertions;

@GwtModule("org.ssg.gui.client.Task")
public class TaskControllerViewTest extends GwtTest {
	
	private TaskControllerView view;

	@Before
	public void setUp() {
		RootPanel rootPanel = RootPanel.get();
		view = new TaskControllerView(rootPanel);
		view.go();
	}
	
	@Test
	public void verifyTaskLabelIsVisible() {
		GwtAssertions.assertThat(view.getTaskLabel()).isNotNull();
	}
	
	@Test
	public void verifyThatSaveButtonIsAccessable() {
		GwtAssertions.assertThat(view.getSaveButton()).isNotNull();
		GwtAssertions.assertThat(view.getSaveButton()).isInstanceOf(Button.class);
		GwtAssertions.assertThat((Button)view.getSaveButton()).htmlContains("Save");
	}
	
	@Test
	public void verifyThatExercisePanelIsAccessable() {
		GwtAssertions.assertThat(view.getExercisePanel()).isNotNull();
	}

}
