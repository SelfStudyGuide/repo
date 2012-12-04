package org.ssg.gui.server.command.handler;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ssg.gui.client.topic.action.GetTopicTasksInfo;
import org.ssg.gui.client.topic.action.GetTopicTasksInfoResponse;

public class GetTopicTasksInfoActionHandlerIntegrationTest extends
        AbstractCommandIntegrationTestCase<GetTopicTasksInfoResponse, GetTopicTasksInfo> {

	@Before
	public void setUp() throws Exception {
		setUpData("/dbunit/GetTopicTasksInfoActionHandlerIntegrationTest.xml");
	}

	@After
	public void tearDown() throws Exception {
		cleanUpData();
	}

	@Test
	public void verifyThatTaskInfosAreLoaded() {
		GetTopicTasksInfoResponse response = whenAction(new GetTopicTasksInfo(10, 40));

		assertThat(response, notNullValue());
		assertThat(response.getTaskInfos().size(), CoreMatchers.is(3));
	}

	@Override
	protected Class<GetTopicTasksInfo> testedCommandClass() {
		return GetTopicTasksInfo.class;
	}

}
