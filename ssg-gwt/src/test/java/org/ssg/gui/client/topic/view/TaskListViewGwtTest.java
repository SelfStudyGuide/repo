package org.ssg.gui.client.topic.view;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class TaskListViewGwtTest extends GWTTestCase {

	private static final String HREF = "http://localhost/task";

	private TaskListView view;
	private RootPanel panel;

	public void gwtSetUp() {
		panel = RootPanel.get();
		view = new TaskListView(panel);
		view.go();
	}

	@Override
	public String getModuleName() {
		return "org.ssg.gui.client.Topic";
	}

	public void testThatViewCanBeIntialised() {
		assertEquals(view, panel.getWidget(0));
	}

	public void testGivenTaskIdWhenGetTextThenInstanceOfAHasTextIsReturned() {
		HasText task = getTask(10);

		// Then
		assertNotNull(task);
	}

	public void testGivenTaskIdWhenGetTextThenPanelWithAnchorShouldBeAddedIntoHorizontalPanel() {
		HasText task = getTask(10);

		// Then
		assertSame("Expented Anchor in the panel", task, getAnchorAt(0));
	}

	public void testGivenHrefThenGetTaskThenTheHrefSholdBePopulatedToAnchor() {
		// Given
		getTask(10);

		// Then
		assertEquals(href(), getAnchorAt(0).getHref());
	}

	private HasText getTask(int taskId) {
		// When
		return view.getTask(taskId, href());
	}

	public void testGivenDifferentTaskIdWhenGetTextThenNewInstanceOfTaskShouldBeReturned() {
		// Given
		HasText task10 = getTask(10);
		HasText task20 = getTask(20);

		// Then
		Anchor anchor20 = getAnchorAt(1);
		assertSame(anchor20, task20);
		assertNotSame(task10, task20);
	}

	public void testGivenSameTaskIdWhenGetTaskThenSameTaskInstanceShouldBeReturned() {
		// Given
		HasText task101 = getTask(10);
		HasText task102 = getTask(10);

		// Then
		Anchor anchor10 = getAnchorAt(0);
		assertSame("Expented same instance of tasks shold be returned", task102, task101);
		assertSame(task101, anchor10);
	}

	private Anchor getAnchorAt(int idx) {
		assertTrue("Expeted " + (idx + 1) + " buttons added", (idx + 1) <= view.topicTaskButtons.getWidgetCount());
		assertTrue("Expented instance of Panel", view.topicTaskButtons.getWidget(idx) instanceof Panel);
		Panel panel = (Panel) view.topicTaskButtons.getWidget(idx);
		assertTrue("Expented Anchor in the panel", panel.iterator().hasNext());
		return (Anchor) panel.iterator().next();
	}

	private String href() {
		return HREF;
	}
}
