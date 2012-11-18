package org.ssg.gui.client.topic.view;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;

@GwtModule("org.ssg.gui.client.Topic")
public class TaskListViewTest extends GwtTest {
	
	private TaskListView view;
	
	private static final String HREF = "http://localhost/task";
	
	@Before
	public void setUp() {
		view = new TaskListView(RootPanel.get());
		view.go();
	}
	
	@Test
	@Ignore
	public void testGivenTaskIdWhenGetTextThenInstanceOfAHasTextIsReturned() {
		HasText task = getTask(10);

		// Then
		Assert.assertTrue(task != null);
	}

	@Test
	@Ignore
	public void testGivenTaskIdWhenGetTextThenPanelWithAnchorShouldBeAddedIntoHorizontalPanel() {
		HasText task = getTask(10);

		// Then
		Assert.assertThat("Expented Anchor in the panel", task, CoreMatchers.is((HasText)getAnchorAt(0)));
	}

	@Test
	@Ignore
	public void testGivenHrefThenGetTaskThenTheHrefSholdBePopulatedToAnchor() {
		// Given
		getTask(10);

		// Then
		Assert.assertEquals(href(), getAnchorAt(0).getHref());
	}

	private HasText getTask(int taskId) {
		// When
		return view.getTask(taskId, href());
	}
	@Test
	@Ignore
	public void testGivenDifferentTaskIdWhenGetTextThenNewInstanceOfTaskShouldBeReturned() {
		// Given
		HasText task10 = getTask(10);
		HasText task20 = getTask(20);

		// Then
		Anchor anchor20 = getAnchorAt(1);
		Assert.assertSame(anchor20, task20);
		Assert.assertNotSame(task10, task20);
	}
	@Test
	@Ignore
	public void testGivenSameTaskIdWhenGetTaskThenSameTaskInstanceShouldBeReturned() {
		// Given
		HasText task101 = getTask(10);
		HasText task102 = getTask(10);

		// Then
		Anchor anchor10 = getAnchorAt(0);
		Assert.assertSame("Expented same instance of tasks shold be returned", task102, task101);
		Assert.assertSame(task101, anchor10);
	}

	private Anchor getAnchorAt(int idx) {
		Assert.assertTrue("Expeted " + (idx + 1) + " buttons added", (idx + 1) <= view.topicTaskButtons.getWidgetCount());
		Assert.assertTrue("Expented instance of Panel", view.topicTaskButtons.getWidget(idx) instanceof Panel);
		Panel panel = (Panel) view.topicTaskButtons.getWidget(idx);
		Assert.assertTrue("Expented Anchor in the panel", panel.iterator().hasNext());
		return (Anchor) panel.iterator().next();
	}

	private String href() {
		return HREF;
	}
}
