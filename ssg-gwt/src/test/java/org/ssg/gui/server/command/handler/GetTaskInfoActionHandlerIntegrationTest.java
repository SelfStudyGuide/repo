package org.ssg.gui.server.command.handler;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ssg.gui.client.task.action.GetTaskInfo;
import org.ssg.gui.client.task.action.GetTaskInfoResponse;

public class GetTaskInfoActionHandlerIntegrationTest extends
        AbstractCommandIntegrationTestCase<GetTaskInfoResponse, GetTaskInfo> {

	@Before
	public void setUp() throws Exception {
		setUpData("/dbunit/GetTaskInfoActionHandlerIntegrationTest.xml");
	}

	@After
	public void tearDown() throws Exception {
		cleanUpData();
	}

	@Test
	public void verifySunnyDayScenario() {
		// Given
		GetTaskInfo action = new GetTaskInfo(10, 20);

		// When
		GetTaskInfoResponse response = whenAction(action);

		// Then
		Assert.assertThat(response.getTaskInfo().getId(), CoreMatchers.is(20));
	}

	@Override
	protected Class<GetTaskInfo> testedCommandClass() {
		return GetTaskInfo.class;
	}
}
